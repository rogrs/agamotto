import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UnidadeNegocios from './unidade-negocios';
import UnidadeNegociosDetail from './unidade-negocios-detail';
import UnidadeNegociosUpdate from './unidade-negocios-update';
import UnidadeNegociosDeleteDialog from './unidade-negocios-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UnidadeNegociosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UnidadeNegociosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UnidadeNegociosDetail} />
      <ErrorBoundaryRoute path={match.url} component={UnidadeNegocios} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={UnidadeNegociosDeleteDialog} />
  </>
);

export default Routes;
