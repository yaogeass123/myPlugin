package com.polaris.plugin;

import static com.polaris.check.CheckValue.*;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import java.util.Objects;


/**
 * @author polaris
 */
public class MyPlugin extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        String path = Objects.requireNonNull(e.getProject()).getBasePath();
        System.out.println(path);
        String res = checkValue(path);
        System.out.println(res);
        Messages.showMessageDialog(res , "检查结果", Messages.getInformationIcon());
    }
}
