import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class NavbarService {
  public visible = false;
  public returnTab: boolean | undefined;
  public toolbarTitle: string | undefined;
  public titleTab = false;
  public createTab = false;
  public saveTab = false;
  public publishTab = false;
  public exportTab = false;
  public cancelTab = false;
  public registerTab = false;
  public recordTab = false;
  public exportRegisterTab = false;

  public hide() {
    this.visible = false;
  }

  public show() {
    this.visible = true;
  }

  public handleTabs(
    returnTab: boolean,
    toolbarTitle: string | undefined,
    createTab: boolean,
    saveTab: boolean,
    publishTab: boolean,
    exportTab: boolean,
    cancelTab: boolean,
    registerTab: boolean,
    recordTab: boolean,
    exportRegisterTab: boolean,
    ) {
    this.returnTab = returnTab;
    this.toolbarTitle = toolbarTitle;
    this.createTab = createTab;
    this.saveTab = saveTab;
    this.publishTab = publishTab;
    this.exportTab = exportTab;
    this.cancelTab = cancelTab;
    this.registerTab = registerTab;
    this.recordTab = recordTab;
    this.exportRegisterTab = exportRegisterTab;
  }
}
