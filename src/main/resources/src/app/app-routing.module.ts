import {Routes, RouterModule} from "@angular/router";
import {NgModule} from "@angular/core";
import {PrimingComponent} from "./components/priming/priming.component";
import {CurrentPrimingComponent} from "./components/current-priming/current-priming.component";
import {HistoryComponent} from "./components/history/history.component";

const routes: Routes = [
    {
        path: '',
        redirectTo: '/priming',
        pathMatch: 'full'
    },
    {
        path: 'priming',
        component: PrimingComponent,
    },
    {
        path: 'current',
        component: CurrentPrimingComponent,
    },
    {
        path: 'history',
        component: HistoryComponent,
    },
    {
        path: '**',
        redirectTo: ''
    },
];

@NgModule({
    imports: [ RouterModule.forRoot(routes, { useHash: true} )],
    exports: [ RouterModule ]
})
export class AppRoutingModule {}
