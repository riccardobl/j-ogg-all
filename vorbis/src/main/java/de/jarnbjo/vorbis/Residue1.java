/*
 * $ProjectName$
 * $ProjectRevision$
 * -----------------------------------------------------------
 * $Id: Residue1.java,v 1.2 2003/03/16 01:11:12 jarnbjo Exp $
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
 * $Log: Residue1.java,v $
 * Revision 1.2  2003/03/16 01:11:12  jarnbjo
 * no message
 */
package de.jarnbjo.vorbis;

import de.jarnbjo.util.io.BitInputStream;
import java.io.IOException;

class Residue1 extends Residue {
    protected Residue1(BitInputStream source, SetupHeader header)
            throws IOException {
        super(source, header);
    }

    @Override
    protected int getType() {
        return 1;
    }

    @Override
    protected void decodeResidue(VorbisStream vorbis, BitInputStream source,
            Mode mode, int ch, boolean[] doNotDecodeFlags, float[][] vectors)
            throws IOException {
        /* @todo implement */
        throw new UnsupportedOperationException();
    }
}
