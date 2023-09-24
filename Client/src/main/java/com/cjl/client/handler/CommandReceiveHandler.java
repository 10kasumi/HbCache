package com.cjl.client.handler;

import com.cjl.config.ThreadConfig;
import com.cjl.constrants.ResultCode;
import com.cjl.handler.CheckCommandHandler;
import com.cjl.message.LoginRequestMessage;
import com.cjl.message.LoginResponseMessage;
import com.cjl.message.Message;
import com.cjl.message.ResponseMessage;
import com.cjl.tasks.BackupTask;
import com.cjl.utils.ClientPropertiesUtils;
import com.cjl.utils.ServerPropertiesUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class CommandReceiveHandler extends ChannelInboundHandlerAdapter {

    CountDownLatch WAIT_FOR_LOGIN;
    AtomicBoolean LOGIN;
    AtomicBoolean EXIT;
    AtomicBoolean RESOLVE_STATUS;
    Scanner scanner;
    String host;

    ExecutorService backupPool = ThreadConfig.backupThread;

    public CommandReceiveHandler() {
        WAIT_FOR_LOGIN = new CountDownLatch(1);
        LOGIN = new AtomicBoolean(false);
        EXIT = new AtomicBoolean(false);
        RESOLVE_STATUS = new AtomicBoolean(true);
        scanner = new Scanner(System.in);
        host = ClientPropertiesUtils.getServerHost();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws ExecutionException, InterruptedException {
        log.debug("msg: {}", msg);
        if ((msg instanceof LoginResponseMessage)) {
            LoginResponseMessage response = (LoginResponseMessage) msg;
            if (response.isSuccess()) {
                // 如果登录成功
                LOGIN.set(true);
            }
            // 唤醒 system in 线程
            WAIT_FOR_LOGIN.countDown();
        }
        if ((msg instanceof ResponseMessage)) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                ResponseMessage responseMessage = (ResponseMessage) msg;
                int code = responseMessage.getCode();
                if (code == ResultCode.SUCCESS_CODE || code == ResultCode.FAILURE_CODE) {
                    System.out.println(responseMessage.getResult());
                } else if (code == ResultCode.ERROR_CODE) {
                    System.out.println(responseMessage.getErrorMessage());
                }
            }, ThreadConfig.blockThreadPool);
            future.get();
            RESOLVE_STATUS.set(true);
        }
    }

    // 在连接建立后触发 active 事件
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
//                            // 负责接收用户在控制台的输入，负责向服务器发送各种消息
        new Thread(() -> {
            List<Integer> list = Arrays.asList(101, 103, 200, 202, 204, 205, 206, 207, 300, 302, 307, 308,
                    400, 401, 402, 403, 404, 405, 406, 500, 501, 502);
            Set<Integer> backupCode = new HashSet<>(list);
            while (true) {
                String username = "", password = "";
                if (checkHasUser()) {
                    System.out.println("请输入用户名：");
                    username = scanner.nextLine();
                    if (username == null || "".equals(username)) {
                        username = "root";
                    }
                    if (EXIT.get()) {
                        return;
                    }
                    System.out.println("请输入密码：");
                    password = scanner.nextLine();
                    if (EXIT.get()) {
                        return;
                    }
                }
                // 构造消息对象
                LoginRequestMessage message = new LoginRequestMessage(username, password);
                System.out.println(message);
                // 发送消息
                ctx.writeAndFlush(message);
                try {
                    WAIT_FOR_LOGIN.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //如果登录失败
                if (!LOGIN.get()) {
                    ctx.channel().close();
                    return;
                }

                CheckCommandHandler checkCommandHandler = new CheckCommandHandler();
                while (true) {
                    if (RESOLVE_STATUS.get()) {
                        System.out.print(host + "> ");
                        String command = null;
                        try {
                            command = scanner.nextLine();
                            if (command.equals("quit")) {
                                EXIT.set(true);
                            }
                        } catch (Exception e) {
                            break;
                        }
                        if (EXIT.get()) {
                            return;
                        }
                        Message msg = checkCommandHandler.checkCommand(command, ctx.channel());
                        if(backupCode.contains(msg.getMessageType())){
                            backupPool.submit(new BackupTask(command));
                        }
                        RESOLVE_STATUS.set(false);
                    }
                }
            }
        }).start();
    }

    private boolean checkHasUser() {
        String username = ServerPropertiesUtils.getUsername();
        String password = ServerPropertiesUtils.getPassword();
        return username != null && password != null;
    }

    // 在连接断开时触发
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("连接已经断开，按任意键退出..");
        EXIT.set(true);
    }

    // 在出现异常时触发
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.debug("连接已经断开，按任意键退出..{}", cause.getMessage());
        EXIT.set(true);
    }
}
