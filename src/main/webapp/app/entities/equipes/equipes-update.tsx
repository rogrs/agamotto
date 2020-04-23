import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDepartamentos } from 'app/shared/model/departamentos.model';
import { getEntities as getDepartamentos } from 'app/entities/departamentos/departamentos.reducer';
import { getEntity, updateEntity, createEntity, reset } from './equipes.reducer';
import { IEquipes } from 'app/shared/model/equipes.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEquipesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EquipesUpdate = (props: IEquipesUpdateProps) => {
  const [departamentosId, setDepartamentosId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { equipesEntity, departamentos, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/equipes');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getDepartamentos();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...equipesEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="agamottoApp.equipes.home.createOrEditLabel">
            <Translate contentKey="agamottoApp.equipes.home.createOrEditLabel">Create or edit a Equipes</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : equipesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="equipes-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="equipes-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nomeLabel" for="equipes-nome">
                  <Translate contentKey="agamottoApp.equipes.nome">Nome</Translate>
                </Label>
                <AvField
                  id="equipes-nome"
                  type="text"
                  name="nome"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="equipes-departamentos">
                  <Translate contentKey="agamottoApp.equipes.departamentos">Departamentos</Translate>
                </Label>
                <AvInput id="equipes-departamentos" type="select" className="form-control" name="departamentos.id">
                  <option value="" key="0" />
                  {departamentos
                    ? departamentos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nome}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/equipes" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  departamentos: storeState.departamentos.entities,
  equipesEntity: storeState.equipes.entity,
  loading: storeState.equipes.loading,
  updating: storeState.equipes.updating,
  updateSuccess: storeState.equipes.updateSuccess
});

const mapDispatchToProps = {
  getDepartamentos,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EquipesUpdate);
