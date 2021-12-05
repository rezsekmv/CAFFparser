package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util;

import org.mockito.Mockito;

public abstract class UserSessionTest {

    static {
        Mockito.mockStatic(ContextUtil.class).when(ContextUtil::getCurrentUsername).thenReturn("sludge");
    }
}
