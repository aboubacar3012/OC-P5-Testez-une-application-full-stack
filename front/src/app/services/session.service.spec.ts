import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionService } from './session.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {HttpClientModule} from "@angular/common/http";
import {SessionInformation} from "../interfaces/sessionInformation.interface";
import {BehaviorSubject, of} from "rxjs";

describe('SessionService', () => {
  let service: SessionService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule,
        HttpClientTestingModule
      ],
      providers: [SessionService]
    });
    service = TestBed.inject(SessionService);
    httpMock = TestBed.inject(HttpTestingController)
  });

  afterEach(() => {
    httpMock.verify();
  })

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('logIn()', () => {
    it('should log in the user and set isLogged to true', () => {
      const user: SessionInformation = { id: 1, username: 'John',  } as unknown as SessionInformation;
      service.logIn(user);
      expect(service.sessionInformation).toEqual(user);
      expect(service.isLogged).toBeTruthy();
    });
  });

  describe('logOut()', () => {
    it('should log out the user and set isLogged to false', () => {
      const user: SessionInformation = { id: 1, username: 'John',  } as unknown as SessionInformation;
      service.logIn(user);
      expect(service.sessionInformation).toEqual(user);
      expect(service.isLogged).toBeTruthy();
      service.logOut();
      expect(service.sessionInformation).toBeUndefined();
      expect(service.isLogged).toBeFalsy();
    });
  });

  describe('$isLogged()', () => {
    it('should return an observable of isLoggedSubject', () => {
      const isLoggedSubjectSpy = jest.spyOn(service['isLoggedSubject'], 'asObservable').mockReturnValue(of());
      service.$isLogged().subscribe(isLogged => {
        expect(isLogged).toEqual(service.isLogged);
      });

      expect(isLoggedSubjectSpy).toHaveBeenCalled();
    });
  });

});
