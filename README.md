# Announce Time

## Introduction

I recently purchased the game Farm Manager 2021.  I became obsessed with the game and played for hours at a time.  I lost track of hjow much time was passing.

The game, like most video games for a computer, uses the full scrren.  I can't see a clock from my computer, so I thought it would be a neat idea to have a clock that announces the time ever hour.

So, I investigated Java text-to-speech and found [FreeTTS](https://freetts.sourceforge.io/).  The library is remarkable.  Unfortuately for me, the default voice is not.  I could barely understand a word the voice would say.  I searched for alternate voices, but couldn't find any.

I put up with the distorted voice for a couple of days, until I came up with the idea to record 23 WAV files of me saying the time.  "It's 12 midnight", "It's 1 am", etc.

I already had Audacity installed on my computer, so I quickly made 23 WAV files.  I used the microphone on my video camera, and my computer is right against the wall, so I sound like I'm inside a box.

The Java Swing GUI is small and simple.

![start](resources-readme/announcetime1.png)

