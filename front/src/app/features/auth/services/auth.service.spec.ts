import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { AuthService } from './auth.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {RegisterRequest} from "../interfaces/registerRequest.interface";
import {LoginRequest} from "../interfaces/loginRequest.interface";
import {SessionInformation} from "../../../interfaces/sessionInformation.interface";

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule,
        HttpClientTestingModule
      ],
      providers: [ AuthService ]
    });
    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController)
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('register', () => {
    it('should send a POST request to the register endpoint with the given register request data', () => {
      const mockRegisterRequest: RegisterRequest = {
        email: 'test@example.com',
        password: 'password',
        firstName:"Aboubacar",
        lastName: "Diallo"
      };

      service.register(mockRegisterRequest).subscribe(() => {});

      const mockHttpRequest = httpMock.expectOne(`${service["pathService"]}/register`);
      expect(mockHttpRequest.request.method).toBe('POST');
      expect(mockHttpRequest.request.body).toEqual(mockRegisterRequest);

      mockHttpRequest.flush({});
    });
  });

  describe('login', () => {
    it('should send a POST request to the login endpoint with the given login request data', () => {
      const mockLoginRequest: LoginRequest = {
        email: 'test@example.com',
        password: 'password'
      };

      service.login(mockLoginRequest).subscribe((sessionInformation: SessionInformation) => {
        expect(sessionInformation).toEqual({
          id: 1,
          email: 'test@example.com',
          admin: false
        });
      });

      const mockHttpRequest = httpMock.expectOne(`${service["pathService"]}/login`);
      expect(mockHttpRequest.request.method).toBe('POST');
      expect(mockHttpRequest.request.body).toEqual(mockLoginRequest);

      mockHttpRequest.flush({
        id: 1,
        email: 'test@example.com',
        admin: false
      });
    });
  });
});
