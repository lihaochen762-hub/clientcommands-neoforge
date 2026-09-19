package net.earthcomputer.clientcommands.mixin.compat;

import com.google.common.collect.ImmutableMap;
import com.mojang.brigadier.suggestion.Suggestion;
import net.earthcomputer.clientcommands.command.Flag;
import net.earthcomputer.clientcommands.interfaces.IClientSuggestionsProvider;
import net.earthcomputer.clientcommands.interfaces.IClientSuggestionsProvider_Alias;
import net.neoforged.neoforge.client.ClientCommandSourceStack;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * On Fabric the client command source is the vanilla {@code ClientSuggestionProvider}, which the
 * command flag and alias state is mixed into. On NeoForge the Forgified Fabric API passes its own
 * {@code ClientCommandSourceStack} instead, so the same state has to be added to that class.
 */
@Mixin(ClientCommandSourceStack.class)
public class ClientCommandSourceStackMixin implements IClientSuggestionsProvider, IClientSuggestionsProvider_Alias {
    @Unique
    private ImmutableMap<Flag<?>, Object> flags = ImmutableMap.of();
    @Unique
    private final Set<String> seenAliases = new HashSet<>();

    @SuppressWarnings("unchecked")
    @Override
    public <T> T clientcommands_getFlag(Flag<T> flag) {
        return (T) this.flags.getOrDefault(flag, flag.getDefaultValue());
    }

    @Override
    public <T> IClientSuggestionsProvider clientcommands_withFlag(Flag<T> flag, T value) {
        // NeoForge reuses the same source instance, so the flag is set on it directly.
        this.flags = ImmutableMap.<Flag<?>, Object>builderWithExpectedSize(this.flags.size() + 1)
            .putAll(this.flags)
            .put(flag, value)
            .build();
        return this;
    }

    @Override
    @Nullable
    public List<Suggestion> clientcommands_filterSuggestions(List<Suggestion> suggestions) {
        if (this.flags.isEmpty()) {
            return null;
        }
        return suggestions.stream().filter(suggestion -> {
            String text = suggestion.getText();
            return !Flag.isFlag(text) || this.flags.keySet().stream()
                .noneMatch(arg -> !arg.isRepeatable() && (text.equals(arg.getFlag()) || text.equals(arg.getShortFlag())));
        }).toList();
    }

    @Override
    public void clientcommands_addSeenAlias(String alias) {
        this.seenAliases.add(alias);
    }

    @Override
    public void clientcommands_removeSeenAlias(String alias) {
        this.seenAliases.remove(alias);
    }

    @Override
    public boolean clientcommands_isAliasSeen(String alias) {
        return this.seenAliases.contains(alias);
    }
}
