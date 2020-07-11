package com.tt.miniapp.video.plugin.event;

import com.tt.miniapp.video.plugin.base.IVideoPluginCommand;

public class BasePluginCommand implements IVideoPluginCommand {
  private int command;
  
  private Object params;
  
  public BasePluginCommand(int paramInt, Object paramObject) {
    this.command = paramInt;
    this.params = paramObject;
  }
  
  public int getCommand() {
    return this.command;
  }
  
  public Object getParams() {
    return this.params;
  }
}


/* Location:              C:\Users\august\Desktop\tik\df_miniapp\classes.jar!\com\tt\miniapp\video\plugin\event\BasePluginCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */