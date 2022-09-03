package nl.enjarai.shared_resources.api;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.Objects;

/**
 * An abstract game resource.
 * You'll probably want to use {@link ResourceDirectory} or {@link ResourceFile} instead.
 */
public interface GameResource {
    @Nullable
    default Identifier getId() {
        return GameResourceRegistry.REGISTRY.getId(this);
    }

    /**
     * The subdirectory of <code>.minecraft</code> where this resource usually resides.
     */
    Path getDefaultDirectory();

    /**
     * The display name of the resource, used in the config menu.
     */
    default Text getDisplayName() {
        var id = getId();
        Objects.requireNonNull(id, "Resource should be registered.");

        return Text.of(id.toString());
    }

    /**
     * Whether this resource requires a restart to be changed.
     */
    boolean isRequiresRestart();

    /**
     * If this resource should be enabled by default.
     */
    boolean isDefaultEnabled();

    /**
     * If this resource being changed is experimental and should be used with caution.
     */
    boolean isExperimental();

    /**
     * The callback to be called whenever this resource is changed, may be called even when the resource is not changed.
     */
    default ResourceUpdateCallback getUpdateCallback() {
        return (path) -> {};
    }

    /**
     * Ensures that the resource is ready to be used.
     * Should usually not create files, but should ensure directories exist.
     */
    void ensureExists(Path resourceLocation);

    interface ResourceUpdateCallback {
        void onUpdate(@Nullable Path path);
    }
}
