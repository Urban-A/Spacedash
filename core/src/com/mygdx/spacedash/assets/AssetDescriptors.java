package com.mygdx.spacedash.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;

public class AssetDescriptors {
    public static final AssetDescriptor<Texture> BANNER_IMG =
            new AssetDescriptor<Texture>(AssetPaths.P_BANNER_IMG, Texture.class);
    public static final AssetDescriptor<Texture> BACKGROUND_IMG =
            new AssetDescriptor<Texture>(AssetPaths.P_BACKGROUND_IMG, Texture.class);
    public static final AssetDescriptor<Texture> PLAYER_IMG =
            new AssetDescriptor<Texture>(AssetPaths.P_PLAYER_IMG, Texture.class);
    public static final AssetDescriptor<Texture> PICKUP_IMG =
            new AssetDescriptor<Texture>(AssetPaths.P_PICKUP_IMG, Texture.class);

    private AssetDescriptors() {}
}
