package br.com.extraplays.extracash.placeholder;

import br.com.extraplays.extracash.ExtraCash;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class HookPlaceholder extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "extracash";
    }

    @Override
    public String getAuthor() {
        return "ExtraPlays";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {

        if (p == null) return "";

        if (params.equals("cash")){

            return String.valueOf(ExtraCash.getInstance().getAccountManager().getFormatedBalance(p.getUniqueId().toString()));

        }

        return null;
    }
}
