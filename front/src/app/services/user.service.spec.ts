import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { UserService } from './user.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {User} from "../interfaces/user.interface";

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule,
        HttpClientTestingModule
      ],
      providers: [UserService]
    });
    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get user by id', () => {
    const mockUser: User = {
      id: 1,
      firstName: 'Aboubacar',
      lastName: "Diallo",
      email: 'test@test.com'
    } as unknown as User;

    service.getById('1').subscribe(user => {
      expect(user).toEqual(mockUser);
    });

    const request = httpMock.expectOne(`${service["pathService"]}/1`);
    expect(request.request.method).toBe('GET');
    request.flush(mockUser);
  });

  it('should delete user by id', () => {
    const mockUser: User = {
      id: 1,
      firstName: 'Aboubacar',
      lastName: "Diallo",
      email: 'test@test.com'
    } as unknown as User;

    service.delete('1').subscribe(response => {
      expect(response).toBeUndefined();
    });

    const request = httpMock.expectOne(`${service["pathService"]}/1`);
    expect(request.request.method).toBe('DELETE');
    request.flush(mockUser);

  });
});
