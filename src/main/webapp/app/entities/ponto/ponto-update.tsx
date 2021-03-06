import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IControlePonto } from 'app/shared/model/controle-ponto.model';
import { getEntities as getControlePontos } from 'app/entities/controle-ponto/controle-ponto.reducer';
import { getEntity, updateEntity, createEntity, reset } from './ponto.reducer';
import { IPonto } from 'app/shared/model/ponto.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPontoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PontoUpdate = (props: IPontoUpdateProps) => {
  const [controlePontoId, setControlePontoId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { pontoEntity, controlePontos, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/ponto');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getControlePontos();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...pontoEntity,
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
          <h2 id="agamottoApp.ponto.home.createOrEditLabel">
            <Translate contentKey="agamottoApp.ponto.home.createOrEditLabel">Create or edit a Ponto</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : pontoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="ponto-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="ponto-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="marcacaoLabel" for="ponto-marcacao">
                  <Translate contentKey="agamottoApp.ponto.marcacao">Marcacao</Translate>
                </Label>
                <AvField
                  id="ponto-marcacao"
                  type="text"
                  name="marcacao"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="motivoLabel" for="ponto-motivo">
                  <Translate contentKey="agamottoApp.ponto.motivo">Motivo</Translate>
                </Label>
                <AvInput
                  id="ponto-motivo"
                  type="select"
                  className="form-control"
                  name="motivo"
                  value={(!isNew && pontoEntity.motivo) || 'AJUSTE'}
                >
                  <option value="AJUSTE">{translate('agamottoApp.TipoMotivoAjuste.AJUSTE')}</option>
                  <option value="FALTA">{translate('agamottoApp.TipoMotivoAjuste.FALTA')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="ponto-controlePonto">
                  <Translate contentKey="agamottoApp.ponto.controlePonto">Controle Ponto</Translate>
                </Label>
                <AvInput id="ponto-controlePonto" type="select" className="form-control" name="controlePonto.id">
                  <option value="" key="0" />
                  {controlePontos
                    ? controlePontos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/ponto" replace color="info">
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
  controlePontos: storeState.controlePonto.entities,
  pontoEntity: storeState.ponto.entity,
  loading: storeState.ponto.loading,
  updating: storeState.ponto.updating,
  updateSuccess: storeState.ponto.updateSuccess
});

const mapDispatchToProps = {
  getControlePontos,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PontoUpdate);
