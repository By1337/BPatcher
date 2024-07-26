package org.by1337.bpatcher.patcher.api;

import org.by1337.bpatcher.Version;
import org.by1337.bpatcher.patcher.PatchData;

/**
 * Main interface for a patcher. (Not required to be implemented)
 * <p>
 * Annotate an implementation of this interface with {@link org.by1337.bpatcher.patcher.api.Main}
 * to include it in the bpatcher-lookup.json file.
 * </p>
 */
public interface PatcherInit {
    /**
     * Called before the server starts loading.
     * <p>
     * Note that {@link Version#getCurrent()} will return null, as the server version is not yet known at this stage.
     * </p>
     *
     * @param patchData object for adding patchers/inject classes at runtime
     */
    void onLoad(PatchData patchData);
}
