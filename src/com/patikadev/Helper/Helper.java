package com.patikadev.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    //
    public static int getScreenCenterPoint(String axis, Dimension size) {
        int point;
        switch (axis){
            case "x" -> point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> point = 0;
        }
        return point;
    }

    public static void setLayout() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                } finally {
                    break;
                }

            }
        }
    }




}
