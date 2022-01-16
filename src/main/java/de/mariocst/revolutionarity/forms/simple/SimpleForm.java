package de.mariocst.revolutionarity.forms.simple;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowSimple;
import de.mariocst.revolutionarity.Revolutionarity;
import de.mariocst.revolutionarity.forms.FormHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SimpleForm {
    private final Revolutionarity plugin;

    private final LinkedHashMap<ElementButton, Consumer<Player>> buttons;
    private final String title, content;
    private final Consumer<Player> closeCallback;
    private final BiConsumer<Player, FormResponseSimple> submitCallback;

    public SimpleForm(Revolutionarity plugin, Builder b) {
        this.plugin = plugin;

        this.buttons = b.buttons;
        this.title = b.title;
        this.content = b.content;
        this.closeCallback = b.closeCallback;
        this.submitCallback = b.submitCallback;
    }

    public void send(Player player) {
        FormWindowSimple form = new FormWindowSimple(title, content);
        buttons.keySet().forEach((form::addButton));

        FormHandler.simplePending.put(player.getName(), this);

        player.showFormWindow(form);
        this.plugin.getServer().getScheduler().scheduleDelayedTask(this.plugin, () -> player.sendExperience(player.getExperience()), 20);
    }

    public void setClosed(Player player) {
        if (closeCallback == null) return;
        closeCallback.accept(player);
    }

    public void setSubmitted(Player p, FormResponseSimple r) {
        if (submitCallback == null) return;
        submitCallback.accept(p, r);
    }

    public HashMap<ElementButton, Consumer<Player>> getButtons() {
        return buttons;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public static class Builder {
        private final Revolutionarity plugin;

        private final LinkedHashMap<ElementButton, Consumer<Player>> buttons = new LinkedHashMap<>();
        private final String title, content;
        private Consumer<Player> closeCallback;
        private BiConsumer<Player, FormResponseSimple> submitCallback;

        public Builder(Revolutionarity plugin, String title, String content) {
            this.plugin = plugin;

            this.title = title;
            this.content = content;
        }

        public Builder onClose(Consumer<Player> close) {
            closeCallback = close;
            return this;
        }

        public Builder addButton(ElementButton button) {
            buttons.put(button, null);
            return this;
        }

        public Builder addButton(ElementButton button, Consumer<Player> callback) {
            buttons.put(button, callback);
            return this;
        }

        public Builder onSubmit(BiConsumer<Player, FormResponseSimple> r) {
            submitCallback = r;
            return this;
        }

        public SimpleForm build() {
            return new SimpleForm(this.plugin, this);
        }
    }
}
