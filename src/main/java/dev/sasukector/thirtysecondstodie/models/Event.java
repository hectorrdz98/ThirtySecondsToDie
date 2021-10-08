package dev.sasukector.thirtysecondstodie.models;

import dev.sasukector.thirtysecondstodie.controllers.EventsController;
import dev.sasukector.thirtysecondstodie.controllers.GameController;
import dev.sasukector.thirtysecondstodie.helpers.ServerUtilities;
import lombok.Getter;
import lombok.Setter;

public class Event {

    private @Getter final EventsController.EventType eventType;
    private @Getter final String name;
    private @Getter final GameController.Category category;
    private @Getter final int duration;
    private @Getter @Setter int missingTime;

    public Event(EventsController.EventType eventType, String name, GameController.Category category, int duration) {
        this.eventType = eventType;
        this.name = name;
        this.category = category;
        this.duration = duration;
        this.missingTime = 0;
    }

    public void announce() {
        ServerUtilities.sendBroadcastMessage("Ha tocado el evento " + ServerUtilities.getCategoryColor(this.category) + "§l" + this.name);
    }

}
