import { HttpClientModule } from '@angular/common/http';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';

import { AppComponent } from './app.component';
import {AuthService} from "./features/auth/services/auth.service";
import {SessionService} from "./services/session.service";
import {BehaviorSubject} from "rxjs";


describe.skip('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let authService: AuthService;
  let sessionService: SessionService;
  let sessionSubject: BehaviorSubject<boolean>;

  beforeEach(async () => {
    sessionSubject = new BehaviorSubject<boolean>(false);

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatToolbarModule
      ],
      declarations: [
        AppComponent
      ],
      providers: [
        AuthService,
        SessionService
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    authService = TestBed.inject(AuthService);
    sessionService = TestBed.inject(SessionService);
    jest.spyOn(sessionService, '$isLogged').mockReturnValue(sessionSubject.asObservable());
    fixture.detectChanges();
  });

  it('should create the app', () => {
    expect(component).toBeTruthy();
  });

  it('should call logout function on button click', () => {
    jest.spyOn(component, 'logout');
    const button = fixture.nativeElement.querySelector('button');
    button.click();
    expect(component.logout).toHaveBeenCalled();
  });
});
