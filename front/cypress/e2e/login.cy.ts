describe('Login spec', () => {
  it('Login successfull', () => {
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
        admin: true
      },
    })

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      {
        body:[{"id":1,"name":"Session essaie","date":"2023-03-24T00:00:00.000+00:00","teacher_id":1,"description":"Apprennez les bases du yoga Apprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yogaApprennez les bases du yoga","users":[2,3],"createdAt":"2023-03-23T17:22:01","updatedAt":"2023-03-23T17:29:21"},{"id":3,"name":"session du soir","date":"2023-03-24T00:00:00.000+00:00","teacher_id":1,"description":"session du soir session du soir session du soir session du soir session du soir session du soir","users":[],"createdAt":"2023-03-23T17:24:33","updatedAt":"2023-03-23T17:24:33"}]
      },
      []).as('session')

    cy.get('input[formControlName=email]').type("")
    
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')
  })

  it('email or password invalid', () => {
    cy.visit('/login')

    cy.get('input[formControlName=email]').type("yogastudio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
    cy.get('.error').contains("An error occurred")

  })
});
