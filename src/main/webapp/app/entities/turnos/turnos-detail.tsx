import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './turnos.reducer';
import { ITurnos } from 'app/shared/model/turnos.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITurnosDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TurnosDetail = (props: ITurnosDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { turnosEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="agamottoApp.turnos.detail.title">Turnos</Translate> [<b>{turnosEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="descricaoTurno">
              <Translate contentKey="agamottoApp.turnos.descricaoTurno">Descricao Turno</Translate>
            </span>
          </dt>
          <dd>{turnosEntity.descricaoTurno}</dd>
          <dt>
            <span id="intervaloFlexivel">
              <Translate contentKey="agamottoApp.turnos.intervaloFlexivel">Intervalo Flexivel</Translate>
            </span>
          </dt>
          <dd>{turnosEntity.intervaloFlexivel}</dd>
        </dl>
        <Button tag={Link} to="/turnos" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/turnos/${turnosEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ turnos }: IRootState) => ({
  turnosEntity: turnos.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TurnosDetail);
