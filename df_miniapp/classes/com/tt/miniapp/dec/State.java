package com.tt.miniapp.dec;

import java.io.InputStream;

final class State {
  int accumulator32;
  
  long accumulator64;
  
  int bitOffset;
  
  int[] blockTrees;
  
  byte[] byteBuffer;
  
  int commandBlockLength;
  
  int contextLookupOffset1;
  
  int contextLookupOffset2;
  
  byte[] contextMap;
  
  int contextMapSlice;
  
  byte[] contextModes;
  
  int copyLength;
  
  byte[] distContextMap;
  
  int distContextMapSlice;
  
  int distRbIdx;
  
  int distance;
  
  int distanceBlockLength;
  
  int distanceCode;
  
  int distancePostfixBits;
  
  int distancePostfixMask;
  
  int endOfStreamReached;
  
  int expectedTotalSize;
  
  int[] hGroup0;
  
  int[] hGroup1;
  
  int[] hGroup2;
  
  int halfOffset;
  
  InputStream input;
  
  int inputEnd;
  
  int insertLength;
  
  int[] intBuffer;
  
  int isEager;
  
  int isMetadata;
  
  int isUncompressed;
  
  int j;
  
  int literalBlockLength;
  
  int literalTree;
  
  int literalTreeIndex;
  
  int maxBackwardDistance;
  
  int maxDistance;
  
  int maxRingBufferSize;
  
  int metaBlockLength;
  
  int nextRunningState;
  
  int numCommandBlockTypes;
  
  int numDirectDistanceCodes;
  
  int numDistanceBlockTypes;
  
  int numLiteralBlockTypes;
  
  byte[] output;
  
  int outputLength;
  
  int outputOffset;
  
  int outputUsed;
  
  int pos;
  
  byte[] ringBuffer = new byte[0];
  
  int ringBufferBytesReady;
  
  int ringBufferBytesWritten;
  
  int ringBufferSize;
  
  int[] rings = new int[10];
  
  int runningState;
  
  short[] shortBuffer;
  
  int tailBytes;
  
  int treeCommandOffset;
  
  int trivialLiteralContext;
  
  State() {
    int[] arrayOfInt = this.rings;
    arrayOfInt[0] = 16;
    arrayOfInt[1] = 15;
    arrayOfInt[2] = 11;
    arrayOfInt[3] = 4;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\dec\State.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */