import { Injectable } from '@angular/core';
import { BasicAuthenticationService } from './basic-authentication.service';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RouteGuardService {

  constructor(
    private basicAuthenticationService: BasicAuthenticationService,
    private router: Router) 
    {
     }


    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      if (this.basicAuthenticationService.isUserLoggedIn())
        return true;
      this.router.navigate(['login']);
      return false;
    }
}
