/*
 * $ProjectName$
 * $ProjectRevision$
 * -----------------------------------------------------------
 * $Id: FlacDecoder.java,v 1.1 2003/03/03 22:06:12 jarnbjo Exp $
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
 * $Log: FlacDecoder.java,v $
 * Revision 1.1  2003/03/03 22:06:12  jarnbjo
 * no message
 */
package de.jarnbjo.jmf;

import de.jarnbjo.flac.FlacStream;
import java.io.IOException;
import javax.media.Buffer;
import javax.media.Codec;
import javax.media.Format;
import javax.media.PlugIn;
import javax.media.format.AudioFormat;

public class FlacDecoder implements Codec {
    private static final String CODEC_NAME = "Flac decoder";

    private static final Format[] supportedInputFormats = {
        new AudioFormat(
        "audio/x-flac",
        Format.NOT_SPECIFIED,
        Format.NOT_SPECIFIED,
        Format.NOT_SPECIFIED,
        Format.NOT_SPECIFIED,
        Format.NOT_SPECIFIED,
        Format.NOT_SPECIFIED,
        Format.NOT_SPECIFIED,
        Format.byteArray),
        new AudioFormat(
        "audio/flac",
        Format.NOT_SPECIFIED,
        Format.NOT_SPECIFIED,
        Format.NOT_SPECIFIED,
        Format.NOT_SPECIFIED,
        Format.NOT_SPECIFIED,
        Format.NOT_SPECIFIED,
        Format.NOT_SPECIFIED,
        Format.byteArray)
    };

    private static final Format[] supportedOutputFormats = {
        new AudioFormat(
        AudioFormat.LINEAR,
        Format.NOT_SPECIFIED,
        Format.NOT_SPECIFIED,
        Format.NOT_SPECIFIED,
        Format.NOT_SPECIFIED,
        Format.NOT_SPECIFIED,
        Format.NOT_SPECIFIED,
        Format.NOT_SPECIFIED,
        Format.byteArray)
    };

    final private FlacStream flacStream = new FlacStream();

    public FlacDecoder() {
    }

    @Override
    public Format[] getSupportedInputFormats() {
        return supportedInputFormats;
    }

    @Override
    public Format[] getSupportedOutputFormats(Format input) {
        if (input == null) {
            return supportedOutputFormats;
        } else {
            AudioFormat[] res = new AudioFormat[1];
            res[0] = new AudioFormat(
                    AudioFormat.LINEAR,
                    ((AudioFormat) input).getSampleRate(),
                    ((AudioFormat) input).getSampleSizeInBits(),
                    ((AudioFormat) input).getChannels(),
                    AudioFormat.BIG_ENDIAN,
                    AudioFormat.SIGNED,
                    Format.NOT_SPECIFIED,
                    Format.NOT_SPECIFIED,
                    Format.byteArray);
            return res;
        }
    }

    @Override
    public int process(Buffer in, Buffer out) {
        //System.out.println("process");
        try {
            byte[] res = flacStream.processPacket((byte[]) in.getData());
            if (res == null) {
                return PlugIn.OUTPUT_BUFFER_NOT_FILLED;
            } else {
                byte[] buffer = (byte[]) out.getData();
                if (buffer == null || res.length > buffer.length) {
                    out.setData(res);
                } else {
                    System.arraycopy(res, 0, buffer, 0, res.length);
                }
                out.setOffset(0);
                out.setLength(res.length);
            }
            return PlugIn.BUFFER_PROCESSED_OK;
        } catch (IOException e) {
            return PlugIn.BUFFER_PROCESSED_FAILED;
        }
    }

    @Override
    public Format setInputFormat(Format format) {
        return format;
    }

    @Override
    public Format setOutputFormat(Format format) {
        return format;
    }

    @Override
    public void open() {
    }

    @Override
    public void close() {
    }

    @Override
    public void reset() {
    }

    @Override
    public String getName() {
        return CODEC_NAME;
    }

    @Override
    public Object getControl(String controlType) {
        return null;
    }

    @Override
    public Object[] getControls() {
        return null;
    }
}
