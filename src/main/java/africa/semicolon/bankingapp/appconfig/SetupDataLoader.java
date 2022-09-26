package africa.semicolon.bankingapp.appconfig;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

public class SetupDataLoader implements ApplicationListener<ContextStartedEvent> {
    @Override
    public void onApplicationEvent(ContextStartedEvent event) {

    }
}
