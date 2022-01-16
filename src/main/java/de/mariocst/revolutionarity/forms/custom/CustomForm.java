package de.mariocst.revolutionarity.forms.custom;

import cn.nukkit.Player;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.window.FormWindowCustom;
import de.mariocst.revolutionarity.Revolutionarity;
import de.mariocst.revolutionarity.forms.FormHandler;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CustomForm {
    private final Revolutionarity plugin;

    private final String title;
    private final ArrayList<Element> elements;
    private final Consumer<Player> closeCallback;
    private final BiConsumer<Player, FormResponseCustom> submitCallback;

    public CustomForm(Revolutionarity plugin, Builder b) {
        this.plugin = plugin;

        this.title = b.title;
        this.elements = b.elements;
        this.closeCallback = b.closeCallback;
        this.submitCallback = b.submitCallback;
    }

    public void send(Player player) {
        FormWindowCustom form = new FormWindowCustom(title);
        elements.forEach(form::addElement);

        FormHandler.customPending.put(player.getName(), this);
        player.showFormWindow(form);

        this.plugin.getServer().getScheduler().scheduleDelayedTask(this.plugin, () -> player.sendExperience(player.getExperience()), 20);
    }

    public void setClosed(Player player) {
        if (closeCallback == null) return;
        closeCallback.accept(player);
    }

    public void setSubmitted(Player player, FormResponseCustom form) {
        submitCallback.accept(player, form);
    }

    public static class Builder {
        private final Revolutionarity plugin;

        private final String title;
        private ArrayList<Element> elements = new ArrayList<>();
        private Consumer<Player> closeCallback;
        private BiConsumer<Player, FormResponseCustom> submitCallback;

        public Builder(Revolutionarity plugin, String title) {
            this.plugin = plugin;
            this.title = title;
        }

        public Builder addElement(Element element) {
            elements.add(element);
            return this;
        }

        public Builder onClose(Consumer<Player> cb) {
            this.closeCallback = cb;
            return this;
        }

        public Builder onSubmit(BiConsumer<Player, FormResponseCustom> cb) {
            this.submitCallback = cb;
            return this;
        }

        public CustomForm build() {
            return new CustomForm(this.plugin, this);
        }
    }
}
