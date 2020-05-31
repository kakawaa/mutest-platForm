package com.mutest.utils;

/**
 * @author guozhengMu
 * @version 1.0
 * @date 2019/3/27 17:43
 * @description
 * @modify
 */
import javax.websocket.Session;

public class TailLogUtil extends Thread {

    private Session session;

    public TailLogUtil(Session session) {
        this.session = session;
    }

    @Override
    public void run() {
        String reportContent = null;
        int i = 0;
        while (i < 100000) {
            i++;
            try {
                Thread.sleep(3000);
                reportContent = System.currentTimeMillis() + "";
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            try {
                session.getBasicRemote().sendText(reportContent);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
