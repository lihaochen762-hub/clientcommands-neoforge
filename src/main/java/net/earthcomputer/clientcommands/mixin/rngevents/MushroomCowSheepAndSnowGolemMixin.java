package net.earthcomputer.clientcommands.mixin.rngevents;

import net.earthcomputer.clientcommands.features.PlayerRandCracker;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.cow.MushroomCow;
import net.minecraft.world.entity.animal.golem.SnowGolem;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Records the player RNG that shearing consumes by damaging the shears.
 *
 * <p>Upstream matches the {@code instanceof ServerLevel} check inside {@code mobInteract}, but
 * NeoForge moves the shearing code of sheep and snow golems into {@code shear(...)}, so that check
 * is only left in {@code MushroomCow#mobInteract}. Instead this hooks the interaction itself and
 * applies the same preconditions vanilla uses to decide whether shearing happens.
 */
@Mixin({MushroomCow.class, Sheep.class, SnowGolem.class})
public class MushroomCowSheepAndSnowGolemMixin {
    @Inject(method = "mobInteract", at = @At("HEAD"))
    public void onInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> ci) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.is(Items.SHEARS)) {
            return;
        }

        boolean readyForShearing = switch ((Object) this) {
            case Sheep sheep -> sheep.readyForShearing();
            case SnowGolem golem -> golem.readyForShearing();
            case MushroomCow cow -> cow.readyForShearing();
            default -> false;
        };
        if (readyForShearing) {
            PlayerRandCracker.onItemDamage(1, player, stack);
        }
    }
}
