import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Equipes from './equipes';
import EquipesDetail from './equipes-detail';
import EquipesUpdate from './equipes-update';
import EquipesDeleteDialog from './equipes-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EquipesDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EquipesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EquipesUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EquipesDetail} />
      <ErrorBoundaryRoute path={match.url} component={Equipes} />
    </Switch>
  </>
);

export default Routes;
