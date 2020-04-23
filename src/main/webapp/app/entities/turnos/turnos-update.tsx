import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './turnos.reducer';
import { ITurnos } from 'app/shared/model/turnos.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITurnosUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TurnosUpdate = (props: ITurnosUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { turnosEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/turnos');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...turnosEntity,
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
          <h2 id="agamottoApp.turnos.home.createOrEditLabel">
            <Translate contentKey="agamottoApp.turnos.home.createOrEditLabel">Create or edit a Turnos</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : turnosEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="turnos-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="turnos-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descricaoTurnoLabel" for="turnos-descricaoTurno">
                  <Translate contentKey="agamottoApp.turnos.descricaoTurno">Descricao Turno</Translate>
                </Label>
                <AvField
                  id="turnos-descricaoTurno"
                  type="text"
                  name="descricaoTurno"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="intervaloFlexivelLabel" for="turnos-intervaloFlexivel">
                  <Translate contentKey="agamottoApp.turnos.intervaloFlexivel">Intervalo Flexivel</Translate>
                </Label>
                <AvInput
                  id="turnos-intervaloFlexivel"
                  type="select"
                  className="form-control"
                  name="intervaloFlexivel"
                  value={(!isNew && turnosEntity.intervaloFlexivel) || 'SIM'}
                >
                  <option value="SIM">{translate('agamottoApp.TipoBoolean.SIM')}</option>
                  <option value="NAO">{translate('agamottoApp.TipoBoolean.NAO')}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/turnos" replace color="info">
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
  turnosEntity: storeState.turnos.entity,
  loading: storeState.turnos.loading,
  updating: storeState.turnos.updating,
  updateSuccess: storeState.turnos.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TurnosUpdate);
