import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IColaboradores } from 'app/shared/model/colaboradores.model';
import { getEntities as getColaboradores } from 'app/entities/colaboradores/colaboradores.reducer';
import { getEntity, updateEntity, createEntity, reset } from './controle-ponto.reducer';
import { IControlePonto } from 'app/shared/model/controle-ponto.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IControlePontoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ControlePontoUpdate = (props: IControlePontoUpdateProps) => {
  const [colaboradorId, setColaboradorId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { controlePontoEntity, colaboradores, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/controle-ponto');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getColaboradores();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...controlePontoEntity,
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
          <h2 id="agamottoApp.controlePonto.home.createOrEditLabel">
            <Translate contentKey="agamottoApp.controlePonto.home.createOrEditLabel">Create or edit a ControlePonto</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : controlePontoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="controle-ponto-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="controle-ponto-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dataLabel" for="controle-ponto-data">
                  <Translate contentKey="agamottoApp.controlePonto.data">Data</Translate>
                </Label>
                <AvField
                  id="controle-ponto-data"
                  type="text"
                  name="data"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="controle-ponto-status">
                  <Translate contentKey="agamottoApp.controlePonto.status">Status</Translate>
                </Label>
                <AvField id="controle-ponto-status" type="text" name="status" />
              </AvGroup>
              <AvGroup>
                <Label for="controle-ponto-colaborador">
                  <Translate contentKey="agamottoApp.controlePonto.colaborador">Colaborador</Translate>
                </Label>
                <AvInput id="controle-ponto-colaborador" type="select" className="form-control" name="colaborador.id">
                  <option value="" key="0" />
                  {colaboradores
                    ? colaboradores.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.nome}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/controle-ponto" replace color="info">
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
  colaboradores: storeState.colaboradores.entities,
  controlePontoEntity: storeState.controlePonto.entity,
  loading: storeState.controlePonto.loading,
  updating: storeState.controlePonto.updating,
  updateSuccess: storeState.controlePonto.updateSuccess
});

const mapDispatchToProps = {
  getColaboradores,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ControlePontoUpdate);
