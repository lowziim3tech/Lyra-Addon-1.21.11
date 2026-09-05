package com.lyradebug.license;

import com.lyradebug.NativeLoader;

public final class NativeGuard {
    private static boolean nativeLoaded;

    private NativeGuard() {
    }

    public static native long[] diffSnapshotsNative(int[][] oldSnapshot, int[][] newSnapshot, int minY, int maxY);

    public static native int computeSwingDurationNative(int originalDuration, float multiplier);

    public static native long[] processChunkBufferNative(int chunkX, int chunkZ, int minY, int scanDepth, int sectionCount, int[] blocks);

    public static boolean Il1lI() {
        return nativeLoaded;
    }

    public static int o0O0o(int originalDuration, float multiplier) {
        if (nativeLoaded) {
            try {
                return computeSwingDurationNative(originalDuration, multiplier);
            } catch (Throwable ignored) {
            }
        }
        return Math.max(1, Math.round((float) originalDuration / multiplier));
    }

    static {
        nativeLoaded = NativeLoader.load();
    }
}
