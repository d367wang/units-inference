import units.UnitsTools;
import units.qual.*;

class RefinementSlotsTest {
    void refineTop(@UnknownUnits int y) {
        y = 10;
        y = UnitsTools.m;
        y = UnitsTools.s;
    }

    void refineMeter(@m Integer y) {
        y = UnitsTools.m;
        y = 10;
        y = null;
    }
}
