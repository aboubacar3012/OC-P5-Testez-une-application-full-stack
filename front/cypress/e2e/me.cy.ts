describe('User details', () => {
  it('me', () => {
    cy.visit('/login')

    cy.intercept({
        method: 'POST',
        url: '/api/auth/login',
      },
      {
        body: {
          id: 1,
          username: 'userName',
          firstName: 'firstName',
          lastName: 'lastName',
          admin: true,
          token: "token"
        },
      }, []).as("login")

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      {
        body: [{
          "id": 1,
          "name": "Session essaie",
          "date": "2023-03-24T00:00:00.000+00:00",
          "teacher_id": 1,
          "description": "Apprennez les bases du yoga Apprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yoga",
          "users": [2, 3],
          "createdAt": "2023-03-23T17:22:01",
          "updatedAt": "2023-03-23T17:29:21"
        }, {
          "id": 3,
          "name": "session du soir",
          "date": "2023-03-24T00:00:00.000+00:00",
          "teacher_id": 1,
          "description": "session du soir session du soir session du soir session du soir session du soir session du soir",
          "users": [],
          "createdAt": "2023-03-23T17:24:33",
          "updatedAt": "2023-03-23T17:24:33"
        }]
      },
      []).as('session')

    cy.intercept({
      method: "GET",
      url: '/api/user/1',
    }, {
      body: {
        admin: true,
        createdAt:"2023-03-23T16:58:03",
        email:"yoga@studio.com",
        firstName:"Admin",
        id:1,
        lastName:"Admin",
        updatedAt:"2023-03-23T16:58:03"
      }
    })

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    cy.url().should('include', "/sessions")
    cy.get(".link").eq(1).click()
  })
})
