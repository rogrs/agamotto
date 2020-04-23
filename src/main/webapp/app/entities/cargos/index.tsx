import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Cargos from './cargos';
import CargosDetail from './cargos-detail';
import CargosUpdate from './cargos-update';
import CargosDeleteDialog from './cargos-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CargosDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CargosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CargosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CargosDetail} />
      <ErrorBoundaryRoute path={match.url} component={Cargos} />
    </Switch>
  </>
);

export default Routes;
