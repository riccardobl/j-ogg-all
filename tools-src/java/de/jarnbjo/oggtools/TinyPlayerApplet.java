/*
 * $ProjectName$
 * $ProjectRevision$
 * -----------------------------------------------------------
 * $Id: TinyPlayerApplet.java,v 1.2 2003/04/10 19:48:40 jarnbjo Exp $
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
 * $Log: TinyPlayerApplet.java,v $
 * Revision 1.2  2003/04/10 19:48:40  jarnbjo
 * no message
 *
 * Revision 1.1  2003/03/31 00:22:29  jarnbjo
 * no message
 *
 * Revision 1.1  2003/03/16 01:10:45  jarnbjo
 * no message
 *
 */

package de.jarnbjo.oggtools;

import java.applet.Applet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import de.jarnbjo.ogg.EndOfOggStreamException;
import de.jarnbjo.ogg.LogicalOggStream;
import de.jarnbjo.ogg.UncachedUrlStream;
import de.jarnbjo.vorbis.VorbisStream;

public class TinyPlayerApplet extends Applet implements Runnable {

   private boolean running=false;
   private boolean initialized=false;
   private VorbisStream vStream;
   private LogicalOggStream loStream;

   @Override
   public void init() {
   }

   @Override
   public void start() {
      new Thread(this).start();
   }

   @Override
   public void stop() {
      running=false;
   }

   @Override
   public void run() {

      try {
         String url=getParameter("url");

         try {
            running=true;

            final UncachedUrlStream os=new UncachedUrlStream(new URL(getCodeBase(), url));
            final LogicalOggStream los=(LogicalOggStream)os.getLogicalStreams().iterator().next();
            final VorbisStream vs=new VorbisStream(los);
            vStream=vs;
            loStream=los;

            initialized=true;

            AudioFormat audioFormat=new AudioFormat(
               (float)vs.getIdentificationHeader().getSampleRate(),
               16,
               vs.getIdentificationHeader().getChannels(),
               true, true);

            DataLine.Info dataLineInfo=new DataLine.Info(SourceDataLine.class, audioFormat);

            SourceDataLine sourceDataLine=(SourceDataLine)AudioSystem.getLine(dataLineInfo);

            sourceDataLine.open(audioFormat);
            sourceDataLine.start();

            VorbisInputStream vis=new VorbisInputStream(vs);
            AudioInputStream ais=new AudioInputStream(vis, audioFormat, -1);

            byte[] buffer=new byte[4096];
            int cnt=0, offset=0;
            int total=0;
            long sampleRate=vs.getIdentificationHeader().getSampleRate();
            long oldLt=0;

            while(running) {
               offset=0;
               while(offset<buffer.length && (cnt = ais.read(buffer, offset, buffer.length-offset))>0) {
                  offset+=cnt;
               }
               if(cnt==-1) {
                  running=false;
               }
               if(offset > 0){
                  sourceDataLine.write(buffer, 0, offset);
                  total+=offset;
               }
               offset=0;
               cnt=0;
            }

            sourceDataLine.drain();
            sourceDataLine.close();
         }
         catch(Exception e) {
            e.printStackTrace();
         }
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }

   public static class VorbisInputStream extends InputStream {

      final private VorbisStream source;

      public VorbisInputStream(VorbisStream source) {
         this.source=source;
      }

      @Override
      public int read() throws IOException {
         return 0;
      }

      @Override
      public int read(byte[] buffer) throws IOException {
         return read(buffer, 0, buffer.length);
      }

      final private static int cnt=0;

      @Override
      public int read(byte[] buffer, int offset, int length) throws IOException {
         try {
            return source.readPcm(buffer, offset, length);
         }
         catch(EndOfOggStreamException e) {
            return -1;
         }
      }
   }

}