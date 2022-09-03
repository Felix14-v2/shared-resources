package nl.enjarai.shared_resources.mixin.options;

import net.minecraft.client.option.GameOptions;
import nl.enjarai.shared_resources.api.GameResourceHelper;
import nl.enjarai.shared_resources.registry.GameResources;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @Mutable
    @Shadow @Final private File optionsFile;

    @Inject(
            method = "<init>",
            at = @At(value = "RETURN")
    )
    private void overwritePath(CallbackInfo ci) {
        var newPath = GameResourceHelper.getPathFor(GameResources.OPTIONS);

        if (newPath != null) {
            optionsFile = newPath.toFile();
        }
    }
}
