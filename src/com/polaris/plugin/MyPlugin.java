package com.polaris.plugin;

import static com.polaris.check.CheckValue.checkValue;

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
        String res = checkValue(path);
        if ("".equals(res)) {
            res = "没有检测到目标值";
        }
        Messages.showMessageDialog(res, "检查结果", Messages.getInformationIcon());
    }
}
