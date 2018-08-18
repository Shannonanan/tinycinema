package co.za.tinycinema.di.application;

import javax.inject.Singleton;
import co.za.tinycinema.di.presentation.PresentationComponent;
import co.za.tinycinema.di.presentation.PresentationModule;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkingModule.class, RoomModule.class})
public interface ApplicationComponent {
    public PresentationComponent newPresentationComponent(PresentationModule presentationModule);



}
