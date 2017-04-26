import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {FormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {PrimingComponent} from "./components/priming/priming.component";
import {HistoryComponent} from "./components/history/history.component";
import {CurrentPrimingComponent} from "./components/current-priming/current-priming.component";
import {AppComponent} from "./components/app/app.component";
import {AppRoutingModule} from "./app-routing.module";
import {RequestFactory} from "./services/request-factory/request-factory.service";
import {JzonbieService} from "./services/jzonbie/jzonbie.service";

@NgModule({
  declarations: [
    AppComponent,
    CurrentPrimingComponent,
    HistoryComponent,
    PrimingComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [
    JzonbieService,
    RequestFactory
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
