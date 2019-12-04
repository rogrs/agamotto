import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Departamentos from './departamentos';
import DepartamentosDetail from './departamentos-detail';
import DepartamentosUpdate from './departamentos-update';
import DepartamentosDeleteDialog from './departamentos-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DepartamentosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DepartamentosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DepartamentosDetail} />
      <ErrorBoundaryRoute path={match.url} component={Departamentos} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DepartamentosDeleteDialog} />
  </>
);

export default Routes;
