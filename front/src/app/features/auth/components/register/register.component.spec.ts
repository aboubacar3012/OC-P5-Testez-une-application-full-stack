import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { expect } from '@jest/globals';

import { RegisterComponent } from './register.component';
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {SessionService} from "../../../../services/session.service";
import {Observable, of} from "rxjs";

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let authServiceMock: jest.Mocked<AuthService>;
  let routerServiceMock: jest.Mocked<Router>

  beforeEach(async () => {
    const authMock = {register: jest.fn()}
    const routerMock = {navigate: jest.fn()}

    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      providers: [
        {provide: AuthService, useValue:authMock},
        {provide: Router, useValue: routerMock },
        FormBuilder
      ],
      imports: [
        BrowserAnimationsModule,
        HttpClientModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    authServiceMock = TestBed.inject(AuthService) as jest.Mocked<AuthService>;
    routerServiceMock = TestBed.inject(Router) as jest.Mocked<Router>;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('returns true when the form meets all the validation criteria', () => {
    const registerRequest =
      {
        email: "test@test.com",
        password: "test!1234",
        firstName: "Aboubacar",
        lastName: "Diallo",
      };
    component.form.setValue(registerRequest)
    expect(component.form.valid).toBe(true)
  })

  it('returns false when the form does not respect all the validation criteria', ()=>{
    const registerRequest =
      {
        email: "testtest.com",
        password: "4",
        firstName: "Aar",
        lastName: "Diallofdsafffffffffffffffffffffffffffffffffffffffffffffffffdfdfdfdffdfd",
      };
    component.form.setValue(registerRequest)
    expect(component.form.valid).not.toBe(true)
  })


  it('should submit register form', () => {
    const registerRequest =
      {
        email: "test@test.com",
        password: "test!1234",
        firstName: "Aboubacar",
        lastName: "Diallo",
      };
    authServiceMock.register.mockReturnValue(of());
    component.form.setValue(registerRequest)
    component.submit();

    expect(authServiceMock.register).toHaveBeenCalledWith(registerRequest)
    expect(component.onError).toBe(false)
    // expect(routerServiceMock.navigate).toHaveBeenCalledWith(["login"]);

    // const routerSpy = jest.spyOn(component['router'], 'navigate');
    // expect(routerSpy).toHaveBeenCalledWith(["/login"]);
  })

  it('should set onError to true on registration error', () => {
    authServiceMock.register.mockReturnValue(
      new Observable<void>((observer) => {
        observer.error('Registration error');
      })
    );

    component.submit();

    expect(component.onError).toBe(true);
  });

});
