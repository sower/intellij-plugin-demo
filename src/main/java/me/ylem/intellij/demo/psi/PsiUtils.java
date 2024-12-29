package me.ylem.intellij.demo.psi;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ex.util.EditorUtil;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiImportStatement;
import com.intellij.psi.PsiJavaCodeReferenceElement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiKeyword;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiSubstitutor;
import com.intellij.psi.PsiType;
import com.intellij.psi.PsiVariable;
import com.intellij.psi.util.PsiFormatUtil;
import com.intellij.psi.util.PsiFormatUtilBase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

/**
 * PsiUtils
 *
 * @since 2024/11/26
 **/
public class PsiUtils {

    public static String getClassSignature(PsiClass psiClass) {
        String type = PsiKeyword.CLASS;
        if (psiClass.isAnnotationType()) {
            type = "@" + PsiKeyword.INTERFACE;
        } else if (psiClass.isInterface()) {
            type = PsiKeyword.INTERFACE;
        } else if (psiClass.isEnum()) {
            type = PsiKeyword.ENUM;
        } else if (psiClass.isRecord()) {
            type = PsiKeyword.RECORD;
        }
        String modifiers = PsiFormatUtil.formatClass(psiClass, PsiFormatUtil.SHOW_MODIFIERS);
        return modifiers + type + PsiFormatUtil.formatClass(psiClass,
            PsiFormatUtil.SHOW_NAME | PsiFormatUtil.SHOW_EXTENDS_IMPLEMENTS);
    }


    public static List<String> getImportedClasses(PsiClass psiClass) {
        PsiJavaFile psiFile = (PsiJavaFile) psiClass.getContainingFile();
        PsiImportList importList = psiFile.getImportList();
        if (importList == null) {
            return Collections.emptyList();
        }
        List<String> importedClasses = new ArrayList<>();
        for (PsiImportStatement importStatement : importList.getImportStatements()) {
            PsiJavaCodeReferenceElement importReference = importStatement.getImportReference();
            if (importReference == null) {
                continue;
            }
            PsiClass resolvedClass = (PsiClass) importReference.resolve();
            if (resolvedClass != null) {
                importedClasses.add(resolvedClass.getQualifiedName());
            }
        }
        return importedClasses;
    }

    public static String getMethodSignature(PsiMethod psiMethod) {
        // 使用 PsiFormatUtil 获取方法的基本签名
        return PsiFormatUtil.formatMethod(psiMethod, PsiSubstitutor.EMPTY,
            PsiFormatUtilBase.SHOW_MODIFIERS | PsiFormatUtilBase.SHOW_NAME
                | PsiFormatUtilBase.SHOW_TYPE | PsiFormatUtilBase.SHOW_PARAMETERS
                | PsiFormatUtilBase.SHOW_THROWS,
            PsiFormatUtilBase.SHOW_TYPE | PsiFormatUtilBase.SHOW_NAME
                | PsiFormatUtilBase.SHOW_MODIFIERS);
    }

    // 获取类的方法注释
    public static String getMethodComment(PsiMethod psiMethod) {
        return psiMethod.getDocComment() != null ? psiMethod.getDocComment().getText() : "";
    }

    // 获取方法体
    public static String getMethodBody(PsiMethod psiMethod) {
        return psiMethod.getBody() != null ? psiMethod.getBody().getText() : "";
    }

    // 获取当前行变量的类
    public static Set<String> getCurLineVarClasses(Editor editor) {
        TextRange textRange = EditorUtil.calcCaretLineTextRange(editor);
        // Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        PsiFile file = PsiManager.getInstance(editor.getProject())
            .findFile(editor.getVirtualFile());
        PsiElement element = file.findElementAt(textRange.getStartOffset());
        HashSet<String> classes = new HashSet<>();
        while (element != null
            && element.getTextRange().getEndOffset() <= textRange.getEndOffset()) {
            String className = getClassName(element);
            if (className != null) {
                classes.add(className);
            } else {
                for (@NotNull PsiElement child : element.getChildren()) {
                    classes.add(getClassName(child));
                }
            }

            element = element.getNextSibling();
        }
        return classes;
    }

    private static String getClassName(PsiElement element) {
        if (element instanceof PsiClass) {
            return ((PsiClass) element).getQualifiedName();
        } else if (element instanceof PsiVariable) {
            PsiType type = ((PsiVariable) element).getType();
            return type.getCanonicalText();
        }
        return null;
    }

}
