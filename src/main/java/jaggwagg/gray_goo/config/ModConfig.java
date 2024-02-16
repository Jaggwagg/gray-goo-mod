package jaggwagg.gray_goo.config;

public class ModConfig {
    private final int empRadius;
    private final boolean allowGooSpread;

    public ModConfig() {
        this.empRadius = 64;
        this.allowGooSpread = true;
    }

    public int getEmpRadius() {
        return this.empRadius;
    }

    public boolean getAllowGooSpread() {
        return this.allowGooSpread;
    }
}
