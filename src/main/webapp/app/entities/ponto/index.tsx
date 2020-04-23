import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Ponto from './ponto';
import PontoDetail from './ponto-detail';
import PontoUpdate from './ponto-update';
import PontoDeleteDialog from './ponto-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PontoDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PontoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PontoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PontoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Ponto} />
    </Switch>
  </>
);

export default Routes;
