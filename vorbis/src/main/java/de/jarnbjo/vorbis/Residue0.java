/*
 * $ProjectName$
 * $ProjectRevision$
 * -----------------------------------------------------------
 * $Id: Residue0.java,v 1.2 2003/03/16 01:11:12 jarnbjo Exp $
 * -----------------------------------------------------------
 *
 * $Author: jarnbjo $
 *
 * Description:
 *
 * Copyright 2002-2003 Tor-Einar Jarnbjo
 * -----------------------------------------------------------
 *
 * Change History
 * -----------------------------------------------------------
 * $Log: Residue0.java,v $
 * Revision 1.2  2003/03/16 01:11:12  jarnbjo
 * no message
 */
package de.jarnbjo.vorbis;

import de.jarnbjo.util.io.BitInputStream;
import java.io.IOException;

class Residue0 extends Residue {
    protected Residue0(BitInputStream source, SetupHeader header)
            throws IOException {
        super(source, header);
    }

    @Override
    protected int getType() {
        return 0;
    }

    @Override
    protected void decodeResidue(VorbisStream vorbis, BitInputStream source,
            Mode mode, int ch, boolean[] doNotDecodeFlags, float[][] vectors)
            throws IOException {
        /* @todo implement */
        throw new UnsupportedOperationException();
    }
}
