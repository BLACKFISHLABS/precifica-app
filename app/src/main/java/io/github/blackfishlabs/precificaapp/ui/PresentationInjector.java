package io.github.blackfishlabs.precificaapp.ui;

import org.greenrobot.eventbus.EventBus;

import io.github.blackfishlabs.precificaapp.BuildConfig;

public class PresentationInjector {

    private PresentationInjector() {/* No instances */}

    private static EventBus sEventBus;

    public static EventBus provideEventBus() {
        if (sEventBus == null) {
            sEventBus = EventBus.builder()
                    .logNoSubscriberMessages(BuildConfig.DEBUG)
                    .sendNoSubscriberEvent(BuildConfig.DEBUG)
                    .build();
        }
        return sEventBus;
    }
}

