package com.tt.miniapp.dec;

import java.io.IOException;
import java.io.InputStream;

final class Decode {
  static final int[] BLOCK_LENGTH_N_BITS;
  
  static final int[] BLOCK_LENGTH_OFFSET;
  
  private static final int[] CODE_LENGTH_CODE_ORDER = new int[] { 
      1, 2, 3, 4, 0, 5, 17, 6, 16, 7, 
      8, 9, 10, 11, 12, 13, 14, 15 };
  
  static final int[] COPY_LENGTH_N_BITS;
  
  static final int[] COPY_LENGTH_OFFSET;
  
  static final int[] COPY_RANGE_LUT;
  
  static final int[] DICTIONARY_OFFSETS_BY_LENGTH;
  
  static final int[] DICTIONARY_SIZE_BITS_BY_LENGTH;
  
  private static final int[] DISTANCE_SHORT_CODE_INDEX_OFFSET = new int[] { 
      3, 2, 1, 0, 3, 3, 3, 3, 3, 3, 
      2, 2, 2, 2, 2, 2 };
  
  private static final int[] DISTANCE_SHORT_CODE_VALUE_OFFSET = new int[] { 
      0, 0, 0, 0, -1, 1, -2, 2, -3, 3, 
      -1, 1, -2, 2, -3, 3 };
  
  private static final int[] FIXED_TABLE = new int[] { 
      131072, 131076, 131075, 196610, 131072, 131076, 131075, 262145, 131072, 131076, 
      131075, 196610, 131072, 131076, 131075, 262149 };
  
  static final int[] INSERT_LENGTH_N_BITS;
  
  static final int[] INSERT_LENGTH_OFFSET;
  
  static final int[] INSERT_RANGE_LUT;
  
  static {
    DICTIONARY_OFFSETS_BY_LENGTH = new int[] { 
        0, 0, 0, 0, 0, 4096, 9216, 21504, 35840, 44032, 
        53248, 63488, 74752, 87040, 93696, 100864, 104704, 106752, 108928, 113536, 
        115968, 118528, 119872, 121280, 122016 };
    DICTIONARY_SIZE_BITS_BY_LENGTH = new int[] { 
        0, 0, 0, 0, 10, 10, 11, 11, 10, 10, 
        10, 10, 10, 9, 9, 8, 7, 7, 8, 7, 
        7, 6, 6, 5, 5 };
    BLOCK_LENGTH_OFFSET = new int[] { 
        1, 5, 9, 13, 17, 25, 33, 41, 49, 65, 
        81, 97, 113, 145, 177, 209, 241, 305, 369, 497, 
        753, 1265, 2289, 4337, 8433, 16625 };
    BLOCK_LENGTH_N_BITS = new int[] { 
        2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 
        4, 4, 5, 5, 5, 5, 6, 6, 7, 8, 
        9, 10, 11, 12, 13, 24 };
    INSERT_LENGTH_OFFSET = new int[] { 
        0, 1, 2, 3, 4, 5, 6, 8, 10, 14, 
        18, 26, 34, 50, 66, 98, 130, 194, 322, 578, 
        1090, 2114, 6210, 22594 };
    INSERT_LENGTH_N_BITS = new int[] { 
        0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 
        3, 3, 4, 4, 5, 5, 6, 7, 8, 9, 
        10, 12, 14, 24 };
    COPY_LENGTH_OFFSET = new int[] { 
        2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 
        14, 18, 22, 30, 38, 54, 70, 102, 134, 198, 
        326, 582, 1094, 2118 };
    COPY_LENGTH_N_BITS = new int[] { 
        0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 
        2, 2, 3, 3, 4, 4, 5, 5, 6, 7, 
        8, 9, 10, 24 };
    INSERT_RANGE_LUT = new int[] { 0, 0, 8, 8, 0, 16, 8, 16, 16 };
    COPY_RANGE_LUT = new int[] { 0, 8, 0, 8, 16, 0, 16, 8, 16 };
  }
  
  private static int calculateFence(State paramState) {
    int j = paramState.ringBufferSize;
    int i = j;
    if (paramState.isEager != 0)
      i = Math.min(j, paramState.ringBufferBytesWritten + paramState.outputLength - paramState.outputUsed); 
    return i;
  }
  
  static int checkDupes(int[] paramArrayOfint, int paramInt) {
    for (int i = 0; i < paramInt - 1; i = j) {
      int j = i + 1;
      int k;
      for (k = j; k < paramInt; k++) {
        if (paramArrayOfint[i] == paramArrayOfint[k])
          return 0; 
      } 
    } 
    return 1;
  }
  
  static void close(State paramState) throws IOException {
    if (paramState.runningState != 0) {
      if (paramState.runningState == 10)
        return; 
      paramState.runningState = 10;
      if (paramState.input != null) {
        Utils.closeInput(paramState.input);
        paramState.input = null;
      } 
      return;
    } 
    throw new IllegalStateException("State MUST be initialized");
  }
  
  private static void copyUncompressedData(State paramState) {
    byte[] arrayOfByte = paramState.ringBuffer;
    if (paramState.metaBlockLength <= 0) {
      BitReader.reload(paramState);
      paramState.runningState = 1;
      return;
    } 
    int i = Math.min(paramState.ringBufferSize - paramState.pos, paramState.metaBlockLength);
    BitReader.copyBytes(paramState, arrayOfByte, paramState.pos, i);
    paramState.metaBlockLength -= i;
    paramState.pos += i;
    if (paramState.pos == paramState.ringBufferSize) {
      paramState.nextRunningState = 5;
      paramState.runningState = 11;
      return;
    } 
    BitReader.reload(paramState);
    paramState.runningState = 1;
  }
  
  private static int decodeBlockTypeAndLength(State paramState, int paramInt1, int paramInt2) {
    int[] arrayOfInt = paramState.rings;
    int j = paramInt1 * 2 + 4;
    BitReader.fillBitWindow(paramState);
    int i = readSymbol(paramState.blockTrees, paramInt1 * 1080, paramState);
    int k = readBlockLength(paramState.blockTrees, (paramInt1 + 3) * 1080, paramState);
    if (i == 1) {
      paramInt1 = arrayOfInt[j + 1] + 1;
    } else if (i == 0) {
      paramInt1 = arrayOfInt[j];
    } else {
      paramInt1 = i - 2;
    } 
    i = paramInt1;
    if (paramInt1 >= paramInt2)
      i = paramInt1 - paramInt2; 
    paramInt1 = j + 1;
    arrayOfInt[j] = arrayOfInt[paramInt1];
    arrayOfInt[paramInt1] = i;
    return k;
  }
  
  private static void decodeCommandBlockSwitch(State paramState) {
    paramState.commandBlockLength = decodeBlockTypeAndLength(paramState, 1, paramState.numCommandBlockTypes);
    paramState.treeCommandOffset = paramState.hGroup1[paramState.rings[7]];
  }
  
  private static int decodeContextMap(int paramInt, byte[] paramArrayOfbyte, State paramState) {
    byte b;
    BitReader.readMoreInput(paramState);
    int j = decodeVarLenUnsignedByte(paramState) + 1;
    if (j == 1) {
      Utils.fillBytesWithZeroes(paramArrayOfbyte, 0, paramInt);
      return j;
    } 
    BitReader.fillBitWindow(paramState);
    if (BitReader.readFewBits(paramState, 1) != 0) {
      b = BitReader.readFewBits(paramState, 4) + 1;
    } else {
      b = 0;
    } 
    int[] arrayOfInt = new int[1080];
    readHuffmanCode(j + b, arrayOfInt, 0, paramState);
    int i;
    label32: for (i = 0; i < paramInt; i++) {
      BitReader.readMoreInput(paramState);
      BitReader.fillBitWindow(paramState);
      int k = readSymbol(arrayOfInt, 0, paramState);
      if (k == 0) {
        paramArrayOfbyte[i] = 0;
      } else {
        if (k <= b) {
          BitReader.fillBitWindow(paramState);
          int m = (1 << k) + BitReader.readFewBits(paramState, k);
          k = i;
          while (true) {
            i = k;
            if (m != 0) {
              if (k < paramInt) {
                paramArrayOfbyte[k] = 0;
                k++;
                m--;
                continue;
              } 
              throw new BrotliRuntimeException("Corrupted context map");
            } 
            continue label32;
          } 
        } 
        paramArrayOfbyte[i] = (byte)(k - b);
      } 
    } 
    BitReader.fillBitWindow(paramState);
    if (BitReader.readFewBits(paramState, 1) == 1)
      inverseMoveToFrontTransform(paramArrayOfbyte, paramInt); 
    return j;
  }
  
  private static void decodeDistanceBlockSwitch(State paramState) {
    paramState.distanceBlockLength = decodeBlockTypeAndLength(paramState, 2, paramState.numDistanceBlockTypes);
    paramState.distContextMapSlice = paramState.rings[9] << 2;
  }
  
  private static int[] decodeHuffmanTreeGroup(int paramInt1, int paramInt2, State paramState) {
    int[] arrayOfInt = new int[paramInt2 * 1080 + paramInt2];
    int i = 0;
    int j = paramInt2;
    while (i < paramInt2) {
      arrayOfInt[i] = j;
      readHuffmanCode(paramInt1, arrayOfInt, j, paramState);
      j += 1080;
      i++;
    } 
    return arrayOfInt;
  }
  
  private static void decodeLiteralBlockSwitch(State paramState) {
    paramState.literalBlockLength = decodeBlockTypeAndLength(paramState, 0, paramState.numLiteralBlockTypes);
    int i = paramState.rings[5];
    paramState.contextMapSlice = i << 6;
    paramState.literalTreeIndex = paramState.contextMap[paramState.contextMapSlice] & 0xFF;
    paramState.literalTree = paramState.hGroup0[paramState.literalTreeIndex];
    paramState.contextLookupOffset1 = paramState.contextModes[i] << 9;
    paramState.contextLookupOffset2 = paramState.contextLookupOffset1 + 256;
  }
  
  private static void decodeMetaBlockLength(State paramState) {
    BitReader.fillBitWindow(paramState);
    paramState.inputEnd = BitReader.readFewBits(paramState, 1);
    int i = 0;
    int j = 0;
    paramState.metaBlockLength = 0;
    paramState.isUncompressed = 0;
    paramState.isMetadata = 0;
    if (paramState.inputEnd != 0 && BitReader.readFewBits(paramState, 1) != 0)
      return; 
    int k = BitReader.readFewBits(paramState, 2) + 4;
    if (k == 7) {
      paramState.isMetadata = 1;
      if (BitReader.readFewBits(paramState, 1) == 0) {
        k = BitReader.readFewBits(paramState, 2);
        i = j;
        if (k == 0)
          return; 
        while (i < k) {
          BitReader.fillBitWindow(paramState);
          j = BitReader.readFewBits(paramState, 8);
          if (j != 0 || i + 1 != k || k <= 1) {
            paramState.metaBlockLength = j << i * 8 | paramState.metaBlockLength;
            i++;
            continue;
          } 
          throw new BrotliRuntimeException("Exuberant nibble");
        } 
      } else {
        throw new BrotliRuntimeException("Corrupted reserved bit");
      } 
    } else {
      while (i < k) {
        BitReader.fillBitWindow(paramState);
        j = BitReader.readFewBits(paramState, 4);
        if (j != 0 || i + 1 != k || k <= 4) {
          paramState.metaBlockLength = j << i * 4 | paramState.metaBlockLength;
          i++;
          continue;
        } 
        throw new BrotliRuntimeException("Exuberant nibble");
      } 
    } 
    paramState.metaBlockLength++;
    if (paramState.inputEnd == 0)
      paramState.isUncompressed = BitReader.readFewBits(paramState, 1); 
  }
  
  private static int decodeVarLenUnsignedByte(State paramState) {
    BitReader.fillBitWindow(paramState);
    if (BitReader.readFewBits(paramState, 1) != 0) {
      int i = BitReader.readFewBits(paramState, 3);
      return (i == 0) ? 1 : (BitReader.readFewBits(paramState, i) + (1 << i));
    } 
    return 0;
  }
  
  private static int decodeWindowBits(State paramState) {
    BitReader.fillBitWindow(paramState);
    if (BitReader.readFewBits(paramState, 1) == 0)
      return 16; 
    int i = BitReader.readFewBits(paramState, 3);
    if (i != 0)
      return i + 17; 
    i = BitReader.readFewBits(paramState, 3);
    return (i != 0) ? (i + 8) : 17;
  }
  
  static void decompress(State paramState) {
    // Byte code:
    //   0: aload_0
    //   1: getfield runningState : I
    //   4: ifeq -> 1732
    //   7: aload_0
    //   8: getfield runningState : I
    //   11: bipush #10
    //   13: if_icmpeq -> 1721
    //   16: aload_0
    //   17: invokestatic calculateFence : (Lcom/tt/miniapp/dec/State;)I
    //   20: istore #7
    //   22: aload_0
    //   23: getfield ringBufferSize : I
    //   26: iconst_1
    //   27: isub
    //   28: istore #8
    //   30: aload_0
    //   31: getfield ringBuffer : [B
    //   34: astore #12
    //   36: aload_0
    //   37: getfield runningState : I
    //   40: bipush #9
    //   42: if_icmpeq -> 1683
    //   45: aload_0
    //   46: getfield runningState : I
    //   49: tableswitch default -> 112, 1 -> 1658, 2 -> 480, 3 -> 489, 4 -> 437, 5 -> 430, 6 -> 659, 7 -> 427, 8 -> 258, 9 -> 112, 10 -> 112, 11 -> 147, 12 -> 168
    //   112: new java/lang/StringBuilder
    //   115: dup
    //   116: ldc_w 'Unexpected state '
    //   119: invokespecial <init> : (Ljava/lang/String;)V
    //   122: astore #12
    //   124: aload #12
    //   126: aload_0
    //   127: getfield runningState : I
    //   130: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   133: pop
    //   134: new com/tt/miniapp/dec/BrotliRuntimeException
    //   137: dup
    //   138: aload #12
    //   140: invokevirtual toString : ()Ljava/lang/String;
    //   143: invokespecial <init> : (Ljava/lang/String;)V
    //   146: athrow
    //   147: aload_0
    //   148: aload_0
    //   149: getfield pos : I
    //   152: aload_0
    //   153: getfield ringBufferSize : I
    //   156: invokestatic min : (II)I
    //   159: putfield ringBufferBytesReady : I
    //   162: aload_0
    //   163: bipush #12
    //   165: putfield runningState : I
    //   168: aload_0
    //   169: invokestatic writeRingBuffer : (Lcom/tt/miniapp/dec/State;)I
    //   172: ifne -> 176
    //   175: return
    //   176: aload_0
    //   177: getfield pos : I
    //   180: aload_0
    //   181: getfield maxBackwardDistance : I
    //   184: if_icmplt -> 195
    //   187: aload_0
    //   188: aload_0
    //   189: getfield maxBackwardDistance : I
    //   192: putfield maxDistance : I
    //   195: aload_0
    //   196: getfield pos : I
    //   199: aload_0
    //   200: getfield ringBufferSize : I
    //   203: if_icmplt -> 247
    //   206: aload_0
    //   207: getfield pos : I
    //   210: aload_0
    //   211: getfield ringBufferSize : I
    //   214: if_icmple -> 231
    //   217: aload #12
    //   219: iconst_0
    //   220: aload_0
    //   221: getfield ringBufferSize : I
    //   224: aload_0
    //   225: getfield pos : I
    //   228: invokestatic copyBytesWithin : ([BIII)V
    //   231: aload_0
    //   232: aload_0
    //   233: getfield pos : I
    //   236: iload #8
    //   238: iand
    //   239: putfield pos : I
    //   242: aload_0
    //   243: iconst_0
    //   244: putfield ringBufferBytesWritten : I
    //   247: aload_0
    //   248: aload_0
    //   249: getfield nextRunningState : I
    //   252: putfield runningState : I
    //   255: goto -> 36
    //   258: aload_0
    //   259: getfield copyLength : I
    //   262: iconst_4
    //   263: if_icmplt -> 416
    //   266: aload_0
    //   267: getfield copyLength : I
    //   270: bipush #24
    //   272: if_icmpgt -> 416
    //   275: getstatic com/tt/miniapp/dec/Decode.DICTIONARY_OFFSETS_BY_LENGTH : [I
    //   278: aload_0
    //   279: getfield copyLength : I
    //   282: iaload
    //   283: istore_1
    //   284: aload_0
    //   285: getfield distance : I
    //   288: aload_0
    //   289: getfield maxDistance : I
    //   292: isub
    //   293: iconst_1
    //   294: isub
    //   295: istore_2
    //   296: getstatic com/tt/miniapp/dec/Decode.DICTIONARY_SIZE_BITS_BY_LENGTH : [I
    //   299: aload_0
    //   300: getfield copyLength : I
    //   303: iaload
    //   304: istore_3
    //   305: iload_2
    //   306: iload_3
    //   307: iushr
    //   308: istore #4
    //   310: aload_0
    //   311: getfield copyLength : I
    //   314: istore #5
    //   316: iload #4
    //   318: bipush #121
    //   320: if_icmpge -> 405
    //   323: aload #12
    //   325: aload_0
    //   326: getfield pos : I
    //   329: invokestatic getData : ()Ljava/nio/ByteBuffer;
    //   332: iconst_1
    //   333: iload_3
    //   334: ishl
    //   335: iconst_1
    //   336: isub
    //   337: iload_2
    //   338: iand
    //   339: iload #5
    //   341: imul
    //   342: iload_1
    //   343: iadd
    //   344: aload_0
    //   345: getfield copyLength : I
    //   348: iload #4
    //   350: invokestatic transformDictionaryWord : ([BILjava/nio/ByteBuffer;III)I
    //   353: istore_1
    //   354: aload_0
    //   355: aload_0
    //   356: getfield pos : I
    //   359: iload_1
    //   360: iadd
    //   361: putfield pos : I
    //   364: aload_0
    //   365: aload_0
    //   366: getfield metaBlockLength : I
    //   369: iload_1
    //   370: isub
    //   371: putfield metaBlockLength : I
    //   374: aload_0
    //   375: getfield pos : I
    //   378: iload #7
    //   380: if_icmplt -> 397
    //   383: aload_0
    //   384: iconst_3
    //   385: putfield nextRunningState : I
    //   388: aload_0
    //   389: bipush #11
    //   391: putfield runningState : I
    //   394: goto -> 36
    //   397: aload_0
    //   398: iconst_3
    //   399: putfield runningState : I
    //   402: goto -> 36
    //   405: new com/tt/miniapp/dec/BrotliRuntimeException
    //   408: dup
    //   409: ldc_w 'Invalid backward reference'
    //   412: invokespecial <init> : (Ljava/lang/String;)V
    //   415: athrow
    //   416: new com/tt/miniapp/dec/BrotliRuntimeException
    //   419: dup
    //   420: ldc_w 'Invalid backward reference'
    //   423: invokespecial <init> : (Ljava/lang/String;)V
    //   426: athrow
    //   427: goto -> 1349
    //   430: aload_0
    //   431: invokestatic copyUncompressedData : (Lcom/tt/miniapp/dec/State;)V
    //   434: goto -> 36
    //   437: aload_0
    //   438: getfield metaBlockLength : I
    //   441: ifle -> 472
    //   444: aload_0
    //   445: invokestatic readMoreInput : (Lcom/tt/miniapp/dec/State;)V
    //   448: aload_0
    //   449: invokestatic fillBitWindow : (Lcom/tt/miniapp/dec/State;)V
    //   452: aload_0
    //   453: bipush #8
    //   455: invokestatic readFewBits : (Lcom/tt/miniapp/dec/State;I)I
    //   458: pop
    //   459: aload_0
    //   460: aload_0
    //   461: getfield metaBlockLength : I
    //   464: iconst_1
    //   465: isub
    //   466: putfield metaBlockLength : I
    //   469: goto -> 437
    //   472: aload_0
    //   473: iconst_1
    //   474: putfield runningState : I
    //   477: goto -> 36
    //   480: aload_0
    //   481: invokestatic readMetablockHuffmanCodesAndContextMaps : (Lcom/tt/miniapp/dec/State;)V
    //   484: aload_0
    //   485: iconst_3
    //   486: putfield runningState : I
    //   489: aload_0
    //   490: getfield metaBlockLength : I
    //   493: ifgt -> 504
    //   496: aload_0
    //   497: iconst_1
    //   498: putfield runningState : I
    //   501: goto -> 36
    //   504: aload_0
    //   505: invokestatic readMoreInput : (Lcom/tt/miniapp/dec/State;)V
    //   508: aload_0
    //   509: getfield commandBlockLength : I
    //   512: ifne -> 519
    //   515: aload_0
    //   516: invokestatic decodeCommandBlockSwitch : (Lcom/tt/miniapp/dec/State;)V
    //   519: aload_0
    //   520: aload_0
    //   521: getfield commandBlockLength : I
    //   524: iconst_1
    //   525: isub
    //   526: putfield commandBlockLength : I
    //   529: aload_0
    //   530: invokestatic fillBitWindow : (Lcom/tt/miniapp/dec/State;)V
    //   533: aload_0
    //   534: getfield hGroup1 : [I
    //   537: aload_0
    //   538: getfield treeCommandOffset : I
    //   541: aload_0
    //   542: invokestatic readSymbol : ([IILcom/tt/miniapp/dec/State;)I
    //   545: istore_3
    //   546: iload_3
    //   547: bipush #6
    //   549: iushr
    //   550: istore_2
    //   551: aload_0
    //   552: iconst_0
    //   553: putfield distanceCode : I
    //   556: iload_2
    //   557: istore_1
    //   558: iload_2
    //   559: iconst_2
    //   560: if_icmplt -> 572
    //   563: iload_2
    //   564: iconst_2
    //   565: isub
    //   566: istore_1
    //   567: aload_0
    //   568: iconst_m1
    //   569: putfield distanceCode : I
    //   572: getstatic com/tt/miniapp/dec/Decode.INSERT_RANGE_LUT : [I
    //   575: iload_1
    //   576: iaload
    //   577: iload_3
    //   578: iconst_3
    //   579: iushr
    //   580: bipush #7
    //   582: iand
    //   583: iadd
    //   584: istore_2
    //   585: aload_0
    //   586: invokestatic fillBitWindow : (Lcom/tt/miniapp/dec/State;)V
    //   589: aload_0
    //   590: getstatic com/tt/miniapp/dec/Decode.INSERT_LENGTH_N_BITS : [I
    //   593: iload_2
    //   594: iaload
    //   595: invokestatic readBits : (Lcom/tt/miniapp/dec/State;I)I
    //   598: istore #4
    //   600: aload_0
    //   601: getstatic com/tt/miniapp/dec/Decode.INSERT_LENGTH_OFFSET : [I
    //   604: iload_2
    //   605: iaload
    //   606: iload #4
    //   608: iadd
    //   609: putfield insertLength : I
    //   612: getstatic com/tt/miniapp/dec/Decode.COPY_RANGE_LUT : [I
    //   615: iload_1
    //   616: iaload
    //   617: iload_3
    //   618: bipush #7
    //   620: iand
    //   621: iadd
    //   622: istore_1
    //   623: aload_0
    //   624: invokestatic fillBitWindow : (Lcom/tt/miniapp/dec/State;)V
    //   627: aload_0
    //   628: getstatic com/tt/miniapp/dec/Decode.COPY_LENGTH_N_BITS : [I
    //   631: iload_1
    //   632: iaload
    //   633: invokestatic readBits : (Lcom/tt/miniapp/dec/State;I)I
    //   636: istore_2
    //   637: aload_0
    //   638: getstatic com/tt/miniapp/dec/Decode.COPY_LENGTH_OFFSET : [I
    //   641: iload_1
    //   642: iaload
    //   643: iload_2
    //   644: iadd
    //   645: putfield copyLength : I
    //   648: aload_0
    //   649: iconst_0
    //   650: putfield j : I
    //   653: aload_0
    //   654: bipush #6
    //   656: putfield runningState : I
    //   659: aload_0
    //   660: getfield trivialLiteralContext : I
    //   663: ifeq -> 770
    //   666: aload_0
    //   667: getfield j : I
    //   670: aload_0
    //   671: getfield insertLength : I
    //   674: if_icmpge -> 767
    //   677: aload_0
    //   678: invokestatic readMoreInput : (Lcom/tt/miniapp/dec/State;)V
    //   681: aload_0
    //   682: getfield literalBlockLength : I
    //   685: ifne -> 692
    //   688: aload_0
    //   689: invokestatic decodeLiteralBlockSwitch : (Lcom/tt/miniapp/dec/State;)V
    //   692: aload_0
    //   693: aload_0
    //   694: getfield literalBlockLength : I
    //   697: iconst_1
    //   698: isub
    //   699: putfield literalBlockLength : I
    //   702: aload_0
    //   703: invokestatic fillBitWindow : (Lcom/tt/miniapp/dec/State;)V
    //   706: aload #12
    //   708: aload_0
    //   709: getfield pos : I
    //   712: aload_0
    //   713: getfield hGroup0 : [I
    //   716: aload_0
    //   717: getfield literalTree : I
    //   720: aload_0
    //   721: invokestatic readSymbol : ([IILcom/tt/miniapp/dec/State;)I
    //   724: i2b
    //   725: bastore
    //   726: aload_0
    //   727: aload_0
    //   728: getfield pos : I
    //   731: iconst_1
    //   732: iadd
    //   733: putfield pos : I
    //   736: aload_0
    //   737: aload_0
    //   738: getfield j : I
    //   741: iconst_1
    //   742: iadd
    //   743: putfield j : I
    //   746: aload_0
    //   747: getfield pos : I
    //   750: iload #7
    //   752: if_icmplt -> 666
    //   755: aload_0
    //   756: bipush #6
    //   758: putfield nextRunningState : I
    //   761: aload_0
    //   762: bipush #11
    //   764: putfield runningState : I
    //   767: goto -> 965
    //   770: aload #12
    //   772: aload_0
    //   773: getfield pos : I
    //   776: iconst_1
    //   777: isub
    //   778: iload #8
    //   780: iand
    //   781: baload
    //   782: sipush #255
    //   785: iand
    //   786: istore_1
    //   787: aload #12
    //   789: aload_0
    //   790: getfield pos : I
    //   793: iconst_2
    //   794: isub
    //   795: iload #8
    //   797: iand
    //   798: baload
    //   799: sipush #255
    //   802: iand
    //   803: istore_2
    //   804: iload_2
    //   805: istore_3
    //   806: iload_1
    //   807: istore_2
    //   808: aload_0
    //   809: getfield j : I
    //   812: aload_0
    //   813: getfield insertLength : I
    //   816: if_icmpge -> 767
    //   819: aload_0
    //   820: invokestatic readMoreInput : (Lcom/tt/miniapp/dec/State;)V
    //   823: aload_0
    //   824: getfield literalBlockLength : I
    //   827: ifne -> 834
    //   830: aload_0
    //   831: invokestatic decodeLiteralBlockSwitch : (Lcom/tt/miniapp/dec/State;)V
    //   834: aload_0
    //   835: getfield contextMap : [B
    //   838: astore #13
    //   840: aload_0
    //   841: getfield contextMapSlice : I
    //   844: istore_1
    //   845: getstatic com/tt/miniapp/dec/Context.LOOKUP : [I
    //   848: aload_0
    //   849: getfield contextLookupOffset1 : I
    //   852: iload_2
    //   853: iadd
    //   854: iaload
    //   855: istore #4
    //   857: aload #13
    //   859: iload_1
    //   860: getstatic com/tt/miniapp/dec/Context.LOOKUP : [I
    //   863: aload_0
    //   864: getfield contextLookupOffset2 : I
    //   867: iload_3
    //   868: iadd
    //   869: iaload
    //   870: iload #4
    //   872: ior
    //   873: iadd
    //   874: baload
    //   875: istore_1
    //   876: aload_0
    //   877: aload_0
    //   878: getfield literalBlockLength : I
    //   881: iconst_1
    //   882: isub
    //   883: putfield literalBlockLength : I
    //   886: aload_0
    //   887: invokestatic fillBitWindow : (Lcom/tt/miniapp/dec/State;)V
    //   890: aload_0
    //   891: getfield hGroup0 : [I
    //   894: aload_0
    //   895: getfield hGroup0 : [I
    //   898: iload_1
    //   899: sipush #255
    //   902: iand
    //   903: iaload
    //   904: aload_0
    //   905: invokestatic readSymbol : ([IILcom/tt/miniapp/dec/State;)I
    //   908: istore_1
    //   909: aload #12
    //   911: aload_0
    //   912: getfield pos : I
    //   915: iload_1
    //   916: i2b
    //   917: bastore
    //   918: aload_0
    //   919: aload_0
    //   920: getfield pos : I
    //   923: iconst_1
    //   924: iadd
    //   925: putfield pos : I
    //   928: aload_0
    //   929: aload_0
    //   930: getfield j : I
    //   933: iconst_1
    //   934: iadd
    //   935: putfield j : I
    //   938: aload_0
    //   939: getfield pos : I
    //   942: iload #7
    //   944: if_icmplt -> 962
    //   947: aload_0
    //   948: bipush #6
    //   950: putfield nextRunningState : I
    //   953: aload_0
    //   954: bipush #11
    //   956: putfield runningState : I
    //   959: goto -> 965
    //   962: goto -> 804
    //   965: aload_0
    //   966: getfield runningState : I
    //   969: bipush #6
    //   971: if_icmpne -> 36
    //   974: aload_0
    //   975: aload_0
    //   976: getfield metaBlockLength : I
    //   979: aload_0
    //   980: getfield insertLength : I
    //   983: isub
    //   984: putfield metaBlockLength : I
    //   987: aload_0
    //   988: getfield metaBlockLength : I
    //   991: ifgt -> 1002
    //   994: aload_0
    //   995: iconst_3
    //   996: putfield runningState : I
    //   999: goto -> 36
    //   1002: aload_0
    //   1003: getfield distanceCode : I
    //   1006: ifge -> 1208
    //   1009: aload_0
    //   1010: invokestatic readMoreInput : (Lcom/tt/miniapp/dec/State;)V
    //   1013: aload_0
    //   1014: getfield distanceBlockLength : I
    //   1017: ifne -> 1024
    //   1020: aload_0
    //   1021: invokestatic decodeDistanceBlockSwitch : (Lcom/tt/miniapp/dec/State;)V
    //   1024: aload_0
    //   1025: aload_0
    //   1026: getfield distanceBlockLength : I
    //   1029: iconst_1
    //   1030: isub
    //   1031: putfield distanceBlockLength : I
    //   1034: aload_0
    //   1035: invokestatic fillBitWindow : (Lcom/tt/miniapp/dec/State;)V
    //   1038: aload_0
    //   1039: getfield hGroup2 : [I
    //   1042: astore #13
    //   1044: aload_0
    //   1045: getfield hGroup2 : [I
    //   1048: astore #14
    //   1050: aload_0
    //   1051: getfield distContextMap : [B
    //   1054: astore #15
    //   1056: aload_0
    //   1057: getfield distContextMapSlice : I
    //   1060: istore_2
    //   1061: aload_0
    //   1062: getfield copyLength : I
    //   1065: iconst_4
    //   1066: if_icmple -> 1074
    //   1069: iconst_3
    //   1070: istore_1
    //   1071: goto -> 1081
    //   1074: aload_0
    //   1075: getfield copyLength : I
    //   1078: iconst_2
    //   1079: isub
    //   1080: istore_1
    //   1081: aload_0
    //   1082: aload #13
    //   1084: aload #14
    //   1086: aload #15
    //   1088: iload_2
    //   1089: iload_1
    //   1090: iadd
    //   1091: baload
    //   1092: sipush #255
    //   1095: iand
    //   1096: iaload
    //   1097: aload_0
    //   1098: invokestatic readSymbol : ([IILcom/tt/miniapp/dec/State;)I
    //   1101: putfield distanceCode : I
    //   1104: aload_0
    //   1105: getfield distanceCode : I
    //   1108: aload_0
    //   1109: getfield numDirectDistanceCodes : I
    //   1112: if_icmplt -> 1208
    //   1115: aload_0
    //   1116: aload_0
    //   1117: getfield distanceCode : I
    //   1120: aload_0
    //   1121: getfield numDirectDistanceCodes : I
    //   1124: isub
    //   1125: putfield distanceCode : I
    //   1128: aload_0
    //   1129: getfield distanceCode : I
    //   1132: istore_1
    //   1133: aload_0
    //   1134: getfield distancePostfixMask : I
    //   1137: istore_2
    //   1138: aload_0
    //   1139: aload_0
    //   1140: getfield distanceCode : I
    //   1143: aload_0
    //   1144: getfield distancePostfixBits : I
    //   1147: iushr
    //   1148: putfield distanceCode : I
    //   1151: aload_0
    //   1152: getfield distanceCode : I
    //   1155: iconst_1
    //   1156: iushr
    //   1157: iconst_1
    //   1158: iadd
    //   1159: istore_3
    //   1160: aload_0
    //   1161: getfield distanceCode : I
    //   1164: istore #4
    //   1166: aload_0
    //   1167: invokestatic fillBitWindow : (Lcom/tt/miniapp/dec/State;)V
    //   1170: aload_0
    //   1171: iload_3
    //   1172: invokestatic readBits : (Lcom/tt/miniapp/dec/State;I)I
    //   1175: istore #5
    //   1177: aload_0
    //   1178: aload_0
    //   1179: getfield numDirectDistanceCodes : I
    //   1182: iload_1
    //   1183: iload_2
    //   1184: iand
    //   1185: iadd
    //   1186: iload #4
    //   1188: iconst_1
    //   1189: iand
    //   1190: iconst_2
    //   1191: iadd
    //   1192: iload_3
    //   1193: ishl
    //   1194: iconst_4
    //   1195: isub
    //   1196: iload #5
    //   1198: iadd
    //   1199: aload_0
    //   1200: getfield distancePostfixBits : I
    //   1203: ishl
    //   1204: iadd
    //   1205: putfield distanceCode : I
    //   1208: aload_0
    //   1209: aload_0
    //   1210: getfield distanceCode : I
    //   1213: aload_0
    //   1214: getfield rings : [I
    //   1217: aload_0
    //   1218: getfield distRbIdx : I
    //   1221: invokestatic translateShortCodes : (I[II)I
    //   1224: putfield distance : I
    //   1227: aload_0
    //   1228: getfield distance : I
    //   1231: iflt -> 1647
    //   1234: aload_0
    //   1235: getfield maxDistance : I
    //   1238: aload_0
    //   1239: getfield maxBackwardDistance : I
    //   1242: if_icmpeq -> 1267
    //   1245: aload_0
    //   1246: getfield pos : I
    //   1249: aload_0
    //   1250: getfield maxBackwardDistance : I
    //   1253: if_icmpge -> 1267
    //   1256: aload_0
    //   1257: aload_0
    //   1258: getfield pos : I
    //   1261: putfield maxDistance : I
    //   1264: goto -> 1275
    //   1267: aload_0
    //   1268: aload_0
    //   1269: getfield maxBackwardDistance : I
    //   1272: putfield maxDistance : I
    //   1275: aload_0
    //   1276: getfield distance : I
    //   1279: aload_0
    //   1280: getfield maxDistance : I
    //   1283: if_icmple -> 1295
    //   1286: aload_0
    //   1287: bipush #8
    //   1289: putfield runningState : I
    //   1292: goto -> 36
    //   1295: aload_0
    //   1296: getfield distanceCode : I
    //   1299: ifle -> 1327
    //   1302: aload_0
    //   1303: getfield rings : [I
    //   1306: aload_0
    //   1307: getfield distRbIdx : I
    //   1310: iconst_3
    //   1311: iand
    //   1312: aload_0
    //   1313: getfield distance : I
    //   1316: iastore
    //   1317: aload_0
    //   1318: aload_0
    //   1319: getfield distRbIdx : I
    //   1322: iconst_1
    //   1323: iadd
    //   1324: putfield distRbIdx : I
    //   1327: aload_0
    //   1328: getfield copyLength : I
    //   1331: aload_0
    //   1332: getfield metaBlockLength : I
    //   1335: if_icmpgt -> 1636
    //   1338: aload_0
    //   1339: iconst_0
    //   1340: putfield j : I
    //   1343: aload_0
    //   1344: bipush #7
    //   1346: putfield runningState : I
    //   1349: iconst_0
    //   1350: istore #4
    //   1352: aload_0
    //   1353: getfield pos : I
    //   1356: aload_0
    //   1357: getfield distance : I
    //   1360: isub
    //   1361: iload #8
    //   1363: iand
    //   1364: istore #5
    //   1366: aload_0
    //   1367: getfield pos : I
    //   1370: istore #6
    //   1372: aload_0
    //   1373: getfield copyLength : I
    //   1376: aload_0
    //   1377: getfield j : I
    //   1380: isub
    //   1381: istore #9
    //   1383: iload #5
    //   1385: iload #9
    //   1387: iadd
    //   1388: istore #10
    //   1390: iload #6
    //   1392: iload #9
    //   1394: iadd
    //   1395: istore #11
    //   1397: iload #10
    //   1399: iload #8
    //   1401: if_icmpge -> 1532
    //   1404: iload #11
    //   1406: iload #8
    //   1408: if_icmpge -> 1532
    //   1411: iload #4
    //   1413: istore_1
    //   1414: iload #5
    //   1416: istore_2
    //   1417: iload #6
    //   1419: istore_3
    //   1420: iload #9
    //   1422: bipush #12
    //   1424: if_icmplt -> 1467
    //   1427: iload #10
    //   1429: iload #6
    //   1431: if_icmple -> 1453
    //   1434: iload #11
    //   1436: iload #5
    //   1438: if_icmple -> 1453
    //   1441: iload #4
    //   1443: istore_1
    //   1444: iload #5
    //   1446: istore_2
    //   1447: iload #6
    //   1449: istore_3
    //   1450: goto -> 1467
    //   1453: aload #12
    //   1455: iload #6
    //   1457: iload #5
    //   1459: iload #10
    //   1461: invokestatic copyBytesWithin : ([BIII)V
    //   1464: goto -> 1496
    //   1467: iload_1
    //   1468: iload #9
    //   1470: if_icmpge -> 1496
    //   1473: aload #12
    //   1475: iload_3
    //   1476: aload #12
    //   1478: iload_2
    //   1479: baload
    //   1480: bastore
    //   1481: iload_1
    //   1482: iconst_1
    //   1483: iadd
    //   1484: istore_1
    //   1485: iload_3
    //   1486: iconst_1
    //   1487: iadd
    //   1488: istore_3
    //   1489: iload_2
    //   1490: iconst_1
    //   1491: iadd
    //   1492: istore_2
    //   1493: goto -> 1467
    //   1496: aload_0
    //   1497: aload_0
    //   1498: getfield j : I
    //   1501: iload #9
    //   1503: iadd
    //   1504: putfield j : I
    //   1507: aload_0
    //   1508: aload_0
    //   1509: getfield metaBlockLength : I
    //   1512: iload #9
    //   1514: isub
    //   1515: putfield metaBlockLength : I
    //   1518: aload_0
    //   1519: aload_0
    //   1520: getfield pos : I
    //   1523: iload #9
    //   1525: iadd
    //   1526: putfield pos : I
    //   1529: goto -> 1619
    //   1532: aload_0
    //   1533: getfield j : I
    //   1536: aload_0
    //   1537: getfield copyLength : I
    //   1540: if_icmpge -> 1619
    //   1543: aload #12
    //   1545: aload_0
    //   1546: getfield pos : I
    //   1549: aload #12
    //   1551: aload_0
    //   1552: getfield pos : I
    //   1555: aload_0
    //   1556: getfield distance : I
    //   1559: isub
    //   1560: iload #8
    //   1562: iand
    //   1563: baload
    //   1564: bastore
    //   1565: aload_0
    //   1566: aload_0
    //   1567: getfield metaBlockLength : I
    //   1570: iconst_1
    //   1571: isub
    //   1572: putfield metaBlockLength : I
    //   1575: aload_0
    //   1576: aload_0
    //   1577: getfield pos : I
    //   1580: iconst_1
    //   1581: iadd
    //   1582: putfield pos : I
    //   1585: aload_0
    //   1586: aload_0
    //   1587: getfield j : I
    //   1590: iconst_1
    //   1591: iadd
    //   1592: putfield j : I
    //   1595: aload_0
    //   1596: getfield pos : I
    //   1599: iload #7
    //   1601: if_icmplt -> 1532
    //   1604: aload_0
    //   1605: bipush #7
    //   1607: putfield nextRunningState : I
    //   1610: aload_0
    //   1611: bipush #11
    //   1613: putfield runningState : I
    //   1616: goto -> 1619
    //   1619: aload_0
    //   1620: getfield runningState : I
    //   1623: bipush #7
    //   1625: if_icmpne -> 36
    //   1628: aload_0
    //   1629: iconst_3
    //   1630: putfield runningState : I
    //   1633: goto -> 36
    //   1636: new com/tt/miniapp/dec/BrotliRuntimeException
    //   1639: dup
    //   1640: ldc_w 'Invalid backward reference'
    //   1643: invokespecial <init> : (Ljava/lang/String;)V
    //   1646: athrow
    //   1647: new com/tt/miniapp/dec/BrotliRuntimeException
    //   1650: dup
    //   1651: ldc_w 'Negative distance'
    //   1654: invokespecial <init> : (Ljava/lang/String;)V
    //   1657: athrow
    //   1658: aload_0
    //   1659: getfield metaBlockLength : I
    //   1662: iflt -> 1672
    //   1665: aload_0
    //   1666: invokestatic readNextMetablockHeader : (Lcom/tt/miniapp/dec/State;)V
    //   1669: goto -> 16
    //   1672: new com/tt/miniapp/dec/BrotliRuntimeException
    //   1675: dup
    //   1676: ldc_w 'Invalid metablock length'
    //   1679: invokespecial <init> : (Ljava/lang/String;)V
    //   1682: athrow
    //   1683: aload_0
    //   1684: getfield runningState : I
    //   1687: bipush #9
    //   1689: if_icmpne -> 1720
    //   1692: aload_0
    //   1693: getfield metaBlockLength : I
    //   1696: iflt -> 1709
    //   1699: aload_0
    //   1700: invokestatic jumpToByteBoundary : (Lcom/tt/miniapp/dec/State;)V
    //   1703: aload_0
    //   1704: iconst_1
    //   1705: invokestatic checkHealth : (Lcom/tt/miniapp/dec/State;I)V
    //   1708: return
    //   1709: new com/tt/miniapp/dec/BrotliRuntimeException
    //   1712: dup
    //   1713: ldc_w 'Invalid metablock length'
    //   1716: invokespecial <init> : (Ljava/lang/String;)V
    //   1719: athrow
    //   1720: return
    //   1721: new java/lang/IllegalStateException
    //   1724: dup
    //   1725: ldc_w 'Can't decompress after close'
    //   1728: invokespecial <init> : (Ljava/lang/String;)V
    //   1731: athrow
    //   1732: new java/lang/IllegalStateException
    //   1735: dup
    //   1736: ldc_w 'Can't decompress until initialized'
    //   1739: invokespecial <init> : (Ljava/lang/String;)V
    //   1742: astore_0
    //   1743: goto -> 1748
    //   1746: aload_0
    //   1747: athrow
    //   1748: goto -> 1746
  }
  
  static void initState(State paramState, InputStream paramInputStream) {
    if (paramState.runningState == 0) {
      paramState.blockTrees = new int[6480];
      paramState.input = paramInputStream;
      BitReader.initBitReader(paramState);
      int i = decodeWindowBits(paramState);
      if (i != 9) {
        paramState.maxRingBufferSize = 1 << i;
        paramState.maxBackwardDistance = paramState.maxRingBufferSize - 16;
        paramState.runningState = 1;
        return;
      } 
      throw new BrotliRuntimeException("Invalid 'windowBits' code");
    } 
    throw new IllegalStateException("State MUST be uninitialized");
  }
  
  private static void inverseMoveToFrontTransform(byte[] paramArrayOfbyte, int paramInt) {
    int i;
    int[] arrayOfInt = new int[256];
    byte b = 0;
    int j = 0;
    while (true) {
      i = b;
      if (j < 256) {
        arrayOfInt[j] = j;
        j++;
        continue;
      } 
      break;
    } 
    while (i < paramInt) {
      j = paramArrayOfbyte[i] & 0xFF;
      paramArrayOfbyte[i] = (byte)arrayOfInt[j];
      if (j != 0)
        moveToFront(arrayOfInt, j); 
      i++;
    } 
  }
  
  private static void maybeReallocateRingBuffer(State paramState) {
    int i = paramState.maxRingBufferSize;
    int j = i;
    if (i > paramState.expectedTotalSize) {
      int k = paramState.expectedTotalSize;
      while (true) {
        j = i >> 1;
        if (j > k) {
          i = j;
          continue;
        } 
        j = i;
        if (paramState.inputEnd == 0) {
          j = i;
          if (i < 16384) {
            j = i;
            if (paramState.maxRingBufferSize >= 16384)
              j = 16384; 
          } 
        } 
        break;
      } 
    } 
    if (j <= paramState.ringBufferSize)
      return; 
    byte[] arrayOfByte = new byte[j + 37];
    if (paramState.ringBuffer.length != 0)
      System.arraycopy(paramState.ringBuffer, 0, arrayOfByte, 0, paramState.ringBufferSize); 
    paramState.ringBuffer = arrayOfByte;
    paramState.ringBufferSize = j;
  }
  
  private static void moveToFront(int[] paramArrayOfint, int paramInt) {
    int i = paramArrayOfint[paramInt];
    while (paramInt > 0) {
      paramArrayOfint[paramInt] = paramArrayOfint[paramInt - 1];
      paramInt--;
    } 
    paramArrayOfint[0] = i;
  }
  
  private static int readBlockLength(int[] paramArrayOfint, int paramInt, State paramState) {
    BitReader.fillBitWindow(paramState);
    paramInt = readSymbol(paramArrayOfint, paramInt, paramState);
    int i = BLOCK_LENGTH_N_BITS[paramInt];
    BitReader.fillBitWindow(paramState);
    return BLOCK_LENGTH_OFFSET[paramInt] + BitReader.readBits(paramState, i);
  }
  
  static void readHuffmanCode(int paramInt1, int[] paramArrayOfint, int paramInt2, State paramState) {
    int i;
    BitReader.readMoreInput(paramState);
    int[] arrayOfInt = new int[paramInt1];
    BitReader.fillBitWindow(paramState);
    int j = BitReader.readFewBits(paramState, 2);
    boolean bool = false;
    if (j == 1) {
      j = paramInt1 - 1;
      int[] arrayOfInt1 = new int[4];
      int k = BitReader.readFewBits(paramState, 2) + 1;
      for (i = 0; j != 0; i++)
        j >>= 1; 
      for (j = 0; j < k; j++) {
        BitReader.fillBitWindow(paramState);
        arrayOfInt1[j] = BitReader.readFewBits(paramState, i) % paramInt1;
        arrayOfInt[arrayOfInt1[j]] = 2;
      } 
      arrayOfInt[arrayOfInt1[0]] = 1;
      if (k != 2) {
        if (k == 4)
          if (BitReader.readFewBits(paramState, 1) == 1) {
            arrayOfInt[arrayOfInt1[2]] = 3;
            arrayOfInt[arrayOfInt1[3]] = 3;
          } else {
            arrayOfInt[arrayOfInt1[0]] = 2;
          }  
      } else {
        arrayOfInt[arrayOfInt1[1]] = 1;
      } 
      i = checkDupes(arrayOfInt1, k);
    } else {
      int[] arrayOfInt1 = new int[18];
      int k = 32;
      for (i = 0; j < 18 && k > 0; i = m) {
        int m = CODE_LENGTH_CODE_ORDER[j];
        BitReader.fillBitWindow(paramState);
        int n = BitReader.peekBits(paramState) & 0xF;
        int i1 = paramState.bitOffset;
        int[] arrayOfInt2 = FIXED_TABLE;
        paramState.bitOffset = i1 + (arrayOfInt2[n] >> 16);
        i1 = arrayOfInt2[n] & 0xFFFF;
        arrayOfInt1[m] = i1;
        n = k;
        m = i;
        if (i1 != 0) {
          n = k - (32 >> i1);
          m = i + 1;
        } 
        j++;
        k = n;
      } 
      if (k != 0 && i != 1) {
        i = bool;
      } else {
        i = 1;
      } 
      readHuffmanCodeLengths(arrayOfInt1, paramInt1, arrayOfInt, paramState);
    } 
    if (i != 0) {
      Huffman.buildHuffmanTable(paramArrayOfint, paramInt2, 8, arrayOfInt, paramInt1);
      return;
    } 
    BrotliRuntimeException brotliRuntimeException = new BrotliRuntimeException("Can't readHuffmanCode");
    throw brotliRuntimeException;
  }
  
  private static void readHuffmanCodeLengths(int[] paramArrayOfint1, int paramInt, int[] paramArrayOfint2, State paramState) {
    int m;
    int[] arrayOfInt = new int[32];
    Huffman.buildHuffmanTable(arrayOfInt, 0, 5, paramArrayOfint1, 18);
    int i = 0;
    int k = 32768;
    int j = 8;
    int n = 0;
    label48: while (true) {
      int i1 = 0;
      m = k;
      while (i < paramInt && m > 0) {
        BitReader.readMoreInput(paramState);
        BitReader.fillBitWindow(paramState);
        k = BitReader.peekBits(paramState) & 0x1F;
        paramState.bitOffset += arrayOfInt[k] >> 16;
        k = arrayOfInt[k] & 0xFFFF;
        if (k < 16) {
          i1 = i + 1;
          paramArrayOfint2[i] = k;
          if (k != 0) {
            m -= 32768 >> k;
            i = i1;
            j = k;
            k = m;
            continue label48;
          } 
          i = i1;
          k = m;
          continue label48;
        } 
        int i3 = k - 14;
        if (k == 16) {
          k = j;
        } else {
          k = 0;
        } 
        int i2 = n;
        if (n != k) {
          i1 = 0;
          i2 = k;
        } 
        if (i1 > 0) {
          k = i1 - 2 << i3;
        } else {
          k = i1;
        } 
        BitReader.fillBitWindow(paramState);
        k += BitReader.readFewBits(paramState, i3) + 3;
        n = k - i1;
        if (i + n <= paramInt) {
          i1 = 0;
          while (i1 < n) {
            paramArrayOfint2[i] = i2;
            i1++;
            i++;
          } 
          i1 = m;
          if (i2 != 0)
            i1 = m - (n << 15 - i2); 
          m = i1;
          n = i2;
          i1 = k;
          continue;
        } 
        throw new BrotliRuntimeException("symbol + repeatDelta > numSymbols");
      } 
      break;
    } 
    if (m == 0) {
      Utils.fillIntsWithZeroes(paramArrayOfint2, i, paramInt);
      return;
    } 
    BrotliRuntimeException brotliRuntimeException = new BrotliRuntimeException("Unused space");
    throw brotliRuntimeException;
  }
  
  private static void readMetablockHuffmanCodesAndContextMaps(State paramState) {
    paramState.numLiteralBlockTypes = decodeVarLenUnsignedByte(paramState) + 1;
    paramState.literalBlockLength = readMetablockPartition(paramState, 0, paramState.numLiteralBlockTypes);
    paramState.numCommandBlockTypes = decodeVarLenUnsignedByte(paramState) + 1;
    paramState.commandBlockLength = readMetablockPartition(paramState, 1, paramState.numCommandBlockTypes);
    paramState.numDistanceBlockTypes = decodeVarLenUnsignedByte(paramState) + 1;
    paramState.distanceBlockLength = readMetablockPartition(paramState, 2, paramState.numDistanceBlockTypes);
    BitReader.readMoreInput(paramState);
    BitReader.fillBitWindow(paramState);
    paramState.distancePostfixBits = BitReader.readFewBits(paramState, 2);
    paramState.numDirectDistanceCodes = (BitReader.readFewBits(paramState, 4) << paramState.distancePostfixBits) + 16;
    paramState.distancePostfixMask = (1 << paramState.distancePostfixBits) - 1;
    int j = paramState.numDirectDistanceCodes;
    int k = paramState.distancePostfixBits;
    paramState.contextModes = new byte[paramState.numLiteralBlockTypes];
    int i = 0;
    while (i < paramState.numLiteralBlockTypes) {
      int n = Math.min(i + 96, paramState.numLiteralBlockTypes);
      while (i < n) {
        BitReader.fillBitWindow(paramState);
        paramState.contextModes[i] = (byte)BitReader.readFewBits(paramState, 2);
        i++;
      } 
      BitReader.readMoreInput(paramState);
    } 
    paramState.contextMap = new byte[paramState.numLiteralBlockTypes << 6];
    int m = decodeContextMap(paramState.numLiteralBlockTypes << 6, paramState.contextMap, paramState);
    paramState.trivialLiteralContext = 1;
    for (i = 0; i < paramState.numLiteralBlockTypes << 6; i++) {
      if (paramState.contextMap[i] != i >> 6) {
        paramState.trivialLiteralContext = 0;
        break;
      } 
    } 
    paramState.distContextMap = new byte[paramState.numDistanceBlockTypes << 2];
    i = decodeContextMap(paramState.numDistanceBlockTypes << 2, paramState.distContextMap, paramState);
    paramState.hGroup0 = decodeHuffmanTreeGroup(256, m, paramState);
    paramState.hGroup1 = decodeHuffmanTreeGroup(704, paramState.numCommandBlockTypes, paramState);
    paramState.hGroup2 = decodeHuffmanTreeGroup(j + (48 << k), i, paramState);
    paramState.contextMapSlice = 0;
    paramState.distContextMapSlice = 0;
    paramState.contextLookupOffset1 = paramState.contextModes[0] << 9;
    paramState.contextLookupOffset2 = paramState.contextLookupOffset1 + 256;
    paramState.literalTreeIndex = 0;
    paramState.literalTree = paramState.hGroup0[0];
    paramState.treeCommandOffset = paramState.hGroup1[0];
    paramState.rings[4] = 1;
    paramState.rings[5] = 0;
    paramState.rings[6] = 1;
    paramState.rings[7] = 0;
    paramState.rings[8] = 1;
    paramState.rings[9] = 0;
  }
  
  private static int readMetablockPartition(State paramState, int paramInt1, int paramInt2) {
    if (paramInt2 <= 1)
      return 268435456; 
    readHuffmanCode(paramInt2 + 2, paramState.blockTrees, paramInt1 * 1080, paramState);
    int[] arrayOfInt = paramState.blockTrees;
    paramInt1 = (paramInt1 + 3) * 1080;
    readHuffmanCode(26, arrayOfInt, paramInt1, paramState);
    return readBlockLength(paramState.blockTrees, paramInt1, paramState);
  }
  
  private static void readNextMetablockHeader(State paramState) {
    if (paramState.inputEnd != 0) {
      paramState.nextRunningState = 9;
      paramState.runningState = 11;
      return;
    } 
    paramState.hGroup0 = new int[0];
    paramState.hGroup1 = new int[0];
    paramState.hGroup2 = new int[0];
    BitReader.readMoreInput(paramState);
    decodeMetaBlockLength(paramState);
    if (paramState.metaBlockLength == 0 && paramState.isMetadata == 0)
      return; 
    if (paramState.isUncompressed != 0 || paramState.isMetadata != 0) {
      byte b;
      BitReader.jumpToByteBoundary(paramState);
      if (paramState.isMetadata != 0) {
        b = 4;
      } else {
        b = 5;
      } 
      paramState.runningState = b;
    } else {
      paramState.runningState = 2;
    } 
    if (paramState.isMetadata != 0)
      return; 
    paramState.expectedTotalSize += paramState.metaBlockLength;
    if (paramState.expectedTotalSize > 1073741824)
      paramState.expectedTotalSize = 1073741824; 
    if (paramState.ringBufferSize < paramState.maxRingBufferSize)
      maybeReallocateRingBuffer(paramState); 
  }
  
  private static int readSymbol(int[] paramArrayOfint, int paramInt, State paramState) {
    int i = BitReader.peekBits(paramState);
    paramInt += i & 0xFF;
    int j = paramArrayOfint[paramInt] >> 16;
    int k = paramArrayOfint[paramInt] & 0xFFFF;
    if (j <= 8) {
      paramState.bitOffset += j;
      return k;
    } 
    paramInt = paramInt + k + ((i & (1 << j) - 1) >>> 8);
    paramState.bitOffset += (paramArrayOfint[paramInt] >> 16) + 8;
    return paramArrayOfint[paramInt] & 0xFFFF;
  }
  
  private static int translateShortCodes(int paramInt1, int[] paramArrayOfint, int paramInt2) {
    return (paramInt1 < 16) ? (paramArrayOfint[paramInt2 + DISTANCE_SHORT_CODE_INDEX_OFFSET[paramInt1] & 0x3] + DISTANCE_SHORT_CODE_VALUE_OFFSET[paramInt1]) : (paramInt1 - 16 + 1);
  }
  
  private static int writeRingBuffer(State paramState) {
    int i = Math.min(paramState.outputLength - paramState.outputUsed, paramState.ringBufferBytesReady - paramState.ringBufferBytesWritten);
    if (i != 0) {
      System.arraycopy(paramState.ringBuffer, paramState.ringBufferBytesWritten, paramState.output, paramState.outputOffset + paramState.outputUsed, i);
      paramState.outputUsed += i;
      paramState.ringBufferBytesWritten += i;
    } 
    return (paramState.outputUsed < paramState.outputLength) ? 1 : 0;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\dec\Decode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */