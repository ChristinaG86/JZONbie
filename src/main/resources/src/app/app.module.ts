import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';

import {AppComponent} from './components/app/app.component';
import {AppRoutingModule} from "./app-routing.module";
import {PrimingComponent} from "./components/priming/priming.component";
import {CurrentPrimingComponent} from "./components/current-priming/current-priming.component";
import {HistoryComponent} from "./components/history/history.component";
import {JZONbieService} from "./services/jzonbie/jzonbie.service";
import {RequestFactory} from "./services/jzonbie/request-factory.service";

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
        JZONbieService,
        RequestFactory
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
