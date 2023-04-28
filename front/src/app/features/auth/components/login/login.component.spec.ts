import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {FormBuilder, ReactiveFormsModule} from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';

import { LoginComponent } from './login.component';
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {SessionInformation} from "../../../../interfaces/sessionInformation.interface";
import {Observable, of} from "rxjs";

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;

  let authServiceMock: jest.Mocked<AuthService>;
  let sessionServiceMock: jest.Mocked<SessionService>;
  let routerServiceMock: jest.Mocked<Router>

  beforeEach(async () => {
    const authMock = {login: jest.fn()}
    const sessionMock = {logIn: jest.fn()}
    const routerMock = {navigate: jest.fn()}

    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        {provide: AuthService, useValue:authMock},
        {provide: SessionService, useValue: sessionMock },
        {provide: Router, useValue: routerMock },
        FormBuilder
      ],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule]
    })
      .compileComponents();
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    authServiceMock = TestBed.inject(AuthService) as jest.Mocked<AuthService>;
    sessionServiceMock = TestBed.inject(SessionService) as jest.Mocked<SessionService>;
    routerServiceMock = TestBed.inject(Router) as jest.Mocked<Router>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return true when email and password is valid', () => {
    component.form.patchValue({email: "yoga@studio.com", password: "test!1234"})
    expect(component.form.valid).toBe(true)
  })

  describe('email unit test suites', () => {


    it('should return false when email is yogastudio.com', () => {
      component.form.patchValue({email: "yogastudio.com", password: "test!1234"})
      expect(component.form.valid).not.toBe(true);
    })

    it('should return false email is empty', () => {
      component.form.patchValue({email: "", password: "test!1234"})
      expect(component.form.valid).not.toBe(true)
    })

    it('should return false when email is not defined', () => {
      component.form.patchValue({password: "test!1234"})
      expect(component.form.valid).not.toBe(true)
    })
  })

  describe('password unit test suites', () => {
    it('should return false when password length less than 3', () => {
      component.form.patchValue({email: "yoga@studio.com", password: ""})
      expect(component.form.valid).not.toBe(true)
    })

    it('should return false when password is not defined', () => {
      component.form.patchValue({email: "yoga@studio.com"})
      expect(component.form.valid).not.toBe(true)
    })
  })

  describe('submit fonction unit test suites', () => {
    it('should submit login form successfully and navigate to sessions page', () => {
      const loginRequest = {email: "yoga@studio.com", password: "test!1234"};
      const sessionInformation = {token: "testtoken", username: "yoga@studio.com"} as SessionInformation;
      authServiceMock.login.mockReturnValue(of(sessionInformation));

      component.form.setValue(loginRequest);
      component.submit();

      expect(authServiceMock.login).toHaveBeenCalledWith(loginRequest);
      expect(sessionServiceMock.logIn).toHaveBeenCalledWith(sessionInformation);
      expect(routerServiceMock.navigate).toHaveBeenCalledWith(["/sessions"]);
      expect(component.onError).toBe(false)
    })

    it('should handle login error and set onError to true', () => {
      const loginRequest = {email: "yogastudio.com", password: "test!1234"};
      authServiceMock.login.mockReturnValue(
        new Observable<SessionInformation>((observer) => {
          observer.error('Registration error');
        })
      );
      component.form.setValue(loginRequest);
      component.submit();

      expect(authServiceMock.login).toHaveBeenCalledWith(loginRequest);
      expect(component.onError).toBe(true);
    })
  })
});
